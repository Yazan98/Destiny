package com.yazan98.culttrip.client.fragment.operations

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.data.models.response.Recipe
import com.yazan98.culttrip.domain.action.RecipeAction
import com.yazan98.culttrip.domain.logic.RecipeViewModel
import com.yazan98.culttrip.domain.state.RecipeState
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.random.VortexImageLoaders
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_recipe.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeFragment @Inject constructor() : VortexFragment<RecipeState, RecipeAction, RecipeViewModel>() {

    private lateinit var viewModel: RecipeViewModel
    private val commentsFragment: RecipeCommentsFragment by lazy {
        RecipeCommentsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_recipe
    }

    override fun initScreen(view: View) {
        viewLifecycleOwner.lifecycleScope.launch {
            arguments?.let {
                it.getLong("RecipeId", 1)?.also {
                    getController().execute(RecipeAction.GetRecipeById(it))
                }
            }
        }

        viewModel.editedPrice.observe(this, Observer { result ->
            RecipePrice?.let {
                it.text = "Price : $result $"
            }
        })

        viewModel.portions.observe(this, Observer { portions ->
            textView7?.let {
                it.text = "Portions (${portions})"
            }
        })

        AddPrice?.apply {
            this.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    getController().execute(RecipeAction.AddPrice())
                }
            }
        }

        button?.apply {
            this.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    getController().execute(RecipeAction.AddToDatabase())
                    executeAddRecipe()
                }
            }
        }

        CommentsButton?.apply {
            this.setOnClickListener {
                viewModel.getStateHandler().value?.let {
                    (it as RecipeState.SuccessState).get().let { recipe ->
                        viewLifecycleOwner.lifecycleScope.launch {
                            commentsFragment.getAllComments(recipe.id)
                        }
                        activity?.let {
                            it.supportFragmentManager?.let {
                                commentsFragment.show(it, "")
                            }
                        }
                    }
                }
            }
        }

    }

    override suspend fun onStateChanged(newState: RecipeState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is RecipeState.SuccessState -> showRecipeInfo(newState.get())
                is RecipeState.ErrorState -> showMessage(newState.get())
            }
        }
    }

    private suspend fun showMessage(message: String) {
        withContext(Dispatchers.Main) {
            activity?.let {
                messageController.showSnackbarWithColor(it, message, R.color.colorPrimary)
            }
        }
    }

    override suspend fun getController(): RecipeViewModel {
        return viewModel
    }

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when (newState) {
                true -> {
                    RecipeLoading?.showView()
                    ContentView?.goneView()
                }

                false -> {
                    RecipeLoading?.goneView()
                    ContentView?.showView()
                }
            }
        }
    }

    private suspend fun executeAddRecipe() {
        withContext(Dispatchers.Main) {
            showMessage(getString(R.string.recipe_success))
            activity?.onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun showRecipeInfo(response: Recipe) {
        withContext(Dispatchers.Main) {
            simpleDraweeView2?.let {
                VortexImageLoaders.loadLargeImageWithFresco(response.image, it, 800, 900)
            }

            textView3?.let {
                it.text = response.name
            }

            textView6?.let {
                it.text = response.description
            }

            TimeRecipe?.let {
                it.text = response.numberOfPieces
            }

            RecipeRating?.let {
                it.text = response.rating.toString()
            }

            RecipePrice?.let {
                it.text = "${response.price} $"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.getRxRepository().clearRepository()
        viewModel.destroyDatabase()
    }

}
