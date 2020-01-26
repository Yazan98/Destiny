package com.yazan98.culttrip.client.fragment.operations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.RecipeCommentAdapter
import com.yazan98.culttrip.data.models.response.RecipeComment
import com.yazan98.culttrip.domain.action.CommentAction
import com.yazan98.culttrip.domain.logic.RecipeCommentsViewModel
import com.yazan98.culttrip.domain.state.CommentState
import com.yazan98.culttrip.domain.state.RecipeState
import io.vortex.android.utils.ui.linearVerticalLayout
import kotlinx.android.synthetic.main.fragment_recipe_comments.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import javax.inject.Inject

class RecipeCommentsFragment @Inject constructor() : BottomSheetDialogFragment() {

    private val viewModel: RecipeCommentsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipe_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStateHandler().observe(viewLifecycleOwner, Observer {
            viewLifecycleOwner.lifecycleScope.launch {
                when (it) {
                    is CommentState.SuccessState -> showAllComments(it.get())
                    is CommentState.ErrorState -> {}
                }
            }
        })
    }

    private suspend fun showAllComments(response: List<RecipeComment>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                CommentsRecycler?.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = RecipeCommentAdapter(response)
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
