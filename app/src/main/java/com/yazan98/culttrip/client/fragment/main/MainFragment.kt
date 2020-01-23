package com.yazan98.culttrip.client.fragment.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.CollectionsAdapter
import com.yazan98.culttrip.data.models.response.Collection
import com.yazan98.culttrip.domain.action.MainAction
import com.yazan98.culttrip.domain.logic.MainViewModel
import com.yazan98.culttrip.domain.state.MainState
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
        GlobalScope.launch {
            getController().execute(MainAction.GetCollection())
        }
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
                is MainState.SuccessState -> showPopularCollections(newState.get())
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

    private suspend fun showPopularCollections(response: List<Collection>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                MainRecyclerCollections.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = CollectionsAdapter(response)
                    (this.adapter as CollectionsAdapter).context = it
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MainRecyclerCollections?.adapter = null
    }

}
