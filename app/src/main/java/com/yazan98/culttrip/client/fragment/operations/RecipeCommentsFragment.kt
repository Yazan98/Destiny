package com.yazan98.culttrip.client.fragment.operations

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.RecipeCommentAdapter
import com.yazan98.culttrip.data.models.response.RecipeComment
import com.yazan98.culttrip.domain.ApplicationConsts
import com.yazan98.culttrip.domain.action.CommentAction
import com.yazan98.culttrip.domain.logic.RecipeCommentsViewModel
import com.yazan98.culttrip.domain.state.CommentState
import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.utils.ui.linearVerticalLayout
import kotlinx.android.synthetic.main.fragment_recipe_comments.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import javax.inject.Inject

class RecipeCommentsFragment @Inject constructor() : BottomSheetDialogFragment() {

    private val viewModel: RecipeCommentsViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_comments, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: BottomSheetDialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.fragment_recipe_comments, null)
        dialog.setContentView(view)
        dialog.setOnShowListener {
            val d = it as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet?.let { it1 ->
                BottomSheetBehavior.from(it1).setState(BottomSheetBehavior.STATE_EXPANDED)
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStateHandler().observe(viewLifecycleOwner, Observer {
            viewLifecycleOwner.lifecycleScope.launch {
                when (it) {
                    is CommentState.SuccessState -> showAllComments(it.get())
                    is CommentState.ErrorState -> showMessage(it.get())
                }
            }
        })

        viewModel.newCommentObserver.observe(viewLifecycleOwner, Observer {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                if (CommentsRecycler?.adapter != null) {
                    (CommentsRecycler?.adapter as RecipeCommentAdapter?)?.addItem(it)
                } else {
                    showAllComments(arrayListOf(it))
                }
                CommentView?.setText("")
            }
        })

        SendCommentButton?.apply {
            this.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    VortexPrefs.get(ApplicationConsts.ID, 0L)?.let {
                        (it as Long)?.let {
                            viewModel.execute(
                                CommentAction.SubmitComment(
                                    CommentView?.text.toString().trim(),
                                    it
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun showMessage(message: String) {
        withContext(Dispatchers.Main) {
            activity?.let {
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet = dialog?.findViewById<View>(R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    private suspend fun showAllComments(response: List<RecipeComment>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                CommentsRecycler?.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = RecipeCommentAdapter(response as ArrayList<RecipeComment>)
                    (this.adapter as RecipeCommentAdapter).context = it
                }
            }
        }
    }

    suspend fun getAllComments(id: Long) {
        withContext(Dispatchers.IO) {
            viewModel.execute(CommentAction.GetCommentsByRecipeId(id))
        }
    }

    override fun onDestroyView() {
        CommentsRecycler?.adapter = null
        viewModel.getRxRepository().clearRepository()
        super.onDestroyView()
    }

}
