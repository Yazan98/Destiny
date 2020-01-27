package com.yazan98.culttrip.client.fragment.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.CategoryAdapter
import com.yazan98.culttrip.client.adapter.OffersAdapter
import com.yazan98.culttrip.client.adapter.RecipesAdapter
import com.yazan98.culttrip.client.adapter.listeners.CategoryListener
import com.yazan98.culttrip.client.screen.OperationsScreen
import com.yazan98.culttrip.data.models.response.Category
import com.yazan98.culttrip.data.models.response.Offer
import com.yazan98.culttrip.data.models.response.Recipe
import com.yazan98.culttrip.data.workers.WorkerStarter
import com.yazan98.culttrip.domain.action.MainAction
import com.yazan98.culttrip.domain.logic.MainViewModel
import com.yazan98.culttrip.domain.state.MainState
import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.linearHorizontalLayout
import io.vortex.android.utils.ui.linearVerticalLayout
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainFragment @Inject constructor() : VortexFragment<MainState, MainAction, MainViewModel>() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        }
    }

    override suspend fun getController(): MainViewModel {
        return viewModel
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main
    }

    override fun initScreen(view: View) {
        viewLifecycleOwner.lifecycleScope.launch {
            getController().execute(MainAction.GetMainPageDetails())
        }

        viewModel.categories.observe(this, Observer {
            viewLifecycleOwner.lifecycleScope.launch {
                showAllCategories(it)
            }
        })

        lifecycleScope.launch(Dispatchers.IO) {
            activity?.let {
                WorkerStarter.startOffers(it)
            }
        }

        viewModel.recipes.observe(this, Observer {
            viewLifecycleOwner.lifecycleScope.launch {
                showRecipesList(it)
            }
        })
    }

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when (newState) {
                true -> {
                    MainContainer?.goneView()
                    MainLoading?.showView()
                }

                false -> {
                    MainContainer?.showView()
                    MainLoading?.goneView()
                }
            }
        }
    }

    override suspend fun onStateChanged(newState: MainState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is MainState.SuccessState -> showAllItems(newState.get())
                is MainState.ErrorState -> showMessage(newState.get())
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

    private suspend fun showAllItems(response: List<Offer>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                MainRecyclerView.apply {
                    this.linearHorizontalLayout(it)
                    this.adapter = OffersAdapter(response)
                    (this.adapter as OffersAdapter).context = it
                }
            }
        }
    }

    private suspend fun showAllCategories(response: List<Category>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                MainRecyclerCollections?.apply {
                    this.linearHorizontalLayout(it)
                    this.adapter = CategoryAdapter(response, object : CategoryListener {
                        override fun onCategoryClicked(id: Long) {
                            GlobalScope.launch {
                                VortexPrefs.put("CategoryId", id.toInt())
                                startScreen<OperationsScreen>(false)
                            }
                        }
                    })
                    (this.adapter as CategoryAdapter).context = it
                }
            }
        }
    }

    private suspend fun showRecipesList(response: List<Recipe>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                RecipesRecycler?.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = RecipesAdapter(response)
                    (this.adapter as RecipesAdapter).context = it
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.getRxRepository().clearRepository()
        (MainRecyclerCollections?.adapter as CategoryAdapter?)?.destroyTheAdapter()
        MainRecyclerCollections?.adapter = null
        MainRecyclerView?.adapter = null
        RecipesRecycler?.adapter = null
    }

}
