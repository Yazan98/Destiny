package com.yazan98.culttrip.domain.logic

import androidx.lifecycle.MutableLiveData
import com.yazan98.culttrip.data.RepositoriesComponentImpl
import com.yazan98.culttrip.data.models.response.Route
import com.yazan98.culttrip.data.repository.DiscoveryRepository
import com.yazan98.culttrip.domain.action.DiscoveryAction
import com.yazan98.culttrip.domain.state.DiscoveryState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DiscoveryViewModel @Inject constructor() : VortexViewModel<DiscoveryState, DiscoveryAction>() {

    val routsObserver: MutableLiveData<List<Route>> by lazy { MutableLiveData<List<Route>>() }
    private val discoveryRepository: DiscoveryRepository by lazy {
        RepositoriesComponentImpl().getDiscoveryRepository()
    }

    override suspend fun execute(newAction: DiscoveryAction) {
        withContext(Dispatchers.IO) {
            if (getStateHandler().value == null || getStateHandler().value is DiscoveryState.ErrorState) {
                getCategories()
                getAllRouts()
            }
        }
    }

    private suspend fun getCategories() {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(discoveryRepository.getAllCategories().subscribe({
                GlobalScope.launch {
                    it.data.let {
                        acceptNewState(DiscoveryState.SuccessState(it))
                    }
                }
            }, {
                GlobalScope.launch {
                    it.message?.let {
                        acceptLoadingState(false)
                        acceptNewState(DiscoveryState.ErrorState(it))
                    }
                }
            }))
        }
    }

    private suspend fun getAllRouts() {
        withContext(Dispatchers.IO) {
            addRxRequest(discoveryRepository.getAllRouts().subscribe({
                GlobalScope.launch {
                    acceptLoadingState(false)
                    it.data.let {
                        routsObserver.postValue(it)
                    }
                }
            }, {
                GlobalScope.launch {
                    it.message?.let {
                        acceptLoadingState(false)
                        acceptNewState(DiscoveryState.ErrorState(it))
                    }
                }
            }))
        }
    }

    override suspend fun getInitialState(): DiscoveryState {
        return DiscoveryState.EmptyState()
    }
}