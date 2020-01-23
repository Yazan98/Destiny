package com.yazan98.culttrip.client.fragment.discover

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.CategoryAdapter
import com.yazan98.culttrip.client.adapter.RoutsAdapter
import com.yazan98.culttrip.data.models.response.Category
import com.yazan98.culttrip.data.models.response.Route
import com.yazan98.culttrip.domain.action.DiscoveryAction
import com.yazan98.culttrip.domain.logic.DiscoveryViewModel
import com.yazan98.culttrip.domain.state.DiscoveryState
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

class DiscoverFragment @Inject constructor() : VortexFragment<DiscoveryState, DiscoveryAction, DiscoveryViewModel>() {

    private lateinit var viewModel: DiscoveryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProvider(this).get(DiscoveryViewModel::class.java)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_discover
    }

    override fun initScreen(view: View) {
        GlobalScope.launch {
            getController().execute(DiscoveryAction.GetDiscoveryAction())
        }

        viewModel.routsObserver.observe(this, Observer {
            GlobalScope.launch {
                showRouts(it)
            }
        })
    }

    override suspend fun getController(): DiscoveryViewModel {
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

    override suspend fun onStateChanged(newState: DiscoveryState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is DiscoveryState.SuccessState -> showCategories(newState.get())
                is DiscoveryState.ErrorState -> showMessage(newState.get())
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

    private suspend fun showCategories(response: List<Category>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                CategoryRecycler?.apply {
                    linearHorizontalLayout(it)
                    this.adapter = CategoryAdapter(response)
                    (this.adapter as CategoryAdapter).context = it
                }
            }
        }
    }

    private suspend fun showRouts(response: List<Route>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                RoutsRecycler?.apply {
                    linearVerticalLayout(it)
                    this.adapter = RoutsAdapter(response)
                    (this.adapter as RoutsAdapter).context = it
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        CategoryRecycler?.adapter = null
        RoutsRecycler?.adapter = null
    }

}
