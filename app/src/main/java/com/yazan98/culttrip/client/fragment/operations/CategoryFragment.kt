package com.yazan98.culttrip.client.fragment.operations

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.PopularRecipesAdapter
import com.yazan98.culttrip.client.adapter.RecipesAdapter
import com.yazan98.culttrip.client.adapter.listeners.RecipeListener
import com.yazan98.culttrip.data.models.response.Recipe
import com.yazan98.culttrip.domain.action.CategoryAction
import com.yazan98.culttrip.domain.logic.CategoryViewModel
import com.yazan98.culttrip.domain.state.CategoryState
import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.linearHorizontalLayout
import io.vortex.android.utils.ui.linearVerticalLayout
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryFragment @Inject constructor() :
    VortexFragment<CategoryState, CategoryAction, CategoryViewModel>() {

    private lateinit var viewModel: CategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_discover
    }

    override fun initScreen(view: View) {
        GlobalScope.launch {
            VortexPrefs.get("CategoryId", 1)?.let {
                getController().execute(CategoryAction.GetRecipesByCategoryId((it as Int).toLong()))
            }

            VortexPrefs.get("CategoryName", "Steak")?.let {
                showToolbarName(it as String)
            }
        }
    }

    private suspend fun showToolbarName(name: String) {
        withContext(Dispatchers.Main) {
            CategoryToolbar?.title = name
        }
    }

    override suspend fun getController(): CategoryViewModel {
        return viewModel
    }

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when (newState) {
                true -> {
                    DiscoveryLoading?.showView()
                    DiscoveryContent?.goneView()
                }

                false -> {
                    DiscoveryLoading?.goneView()
                    DiscoveryContent?.showView()
                }
            }
        }
    }

    override suspend fun onStateChanged(newState: CategoryState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is CategoryState.SuccessState -> {
                    showPopularRecipes(newState.getPopularRecipes())
                    showPromotedRecipes(newState.getPromotedRecipes())
                }
                is CategoryState.ErrorState -> showMessage(newState.get())
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

    private suspend fun showPopularRecipes(response: List<Recipe>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                PopularRecipes?.apply {
                    linearHorizontalLayout(it)
                    this.adapter = PopularRecipesAdapter(response, object : RecipeListener {
                        override fun onRecipeClicked(id: Long) {
                            findNavController().navigate(R.id.action_discoverFragment_to_recipeFragment)
                        }
                    })
                    (this.adapter as PopularRecipesAdapter).context = it
                }
            }
        }
    }

    private suspend fun showPromotedRecipes(response: List<Recipe>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                RoutsRecycler?.apply {
                    linearVerticalLayout(it)
                    this.adapter = RecipesAdapter(response, object : RecipeListener {
                        override fun onRecipeClicked(id: Long) {
                            findNavController().navigate(R.id.action_discoverFragment_to_recipeFragment)
                        }
                    })
                    (this.adapter as RecipesAdapter).context = it
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (PopularRecipes?.adapter as PopularRecipesAdapter).destroy()
        PopularRecipes?.adapter = null
        RoutsRecycler?.adapter = null
    }

}
