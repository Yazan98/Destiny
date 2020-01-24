package com.yazan98.culttrip.domain.logic

import androidx.lifecycle.MutableLiveData
import com.yazan98.culttrip.data.RepositoriesComponentImpl
import com.yazan98.culttrip.data.models.response.Offer
import com.yazan98.culttrip.data.repository.MainRepository
import com.yazan98.culttrip.domain.action.MainAction
import com.yazan98.culttrip.domain.state.MainState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor() : VortexViewModel<MainState, MainAction>() {

    val offers: MutableLiveData<List<Offer>> by lazy { MutableLiveData<List<Offer>>() }
    private val repository: MainRepository by lazy {
        RepositoriesComponentImpl().getCollectionRepository()
    }

    override suspend fun execute(newAction: MainAction) {
        withContext(Dispatchers.IO) {
            if (getStateHandler().value is MainState.ErrorState || getStateHandler().value == null) {
                when (newAction) {
                    is MainAction.GetCollection -> getAllOffers()
                }
            }
        }
    }

    private suspend fun getAllOffers() {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(repository.getOffers().subscribe({
                GlobalScope.launch {
                    it.data?.let {
                        acceptLoadingState(false)
                        acceptNewState(MainState.SuccessState(it))
                    }
                }
            }, {
                GlobalScope.launch {
                    it.message?.let {
                        acceptLoadingState(false)
                        acceptNewState(MainState.ErrorState(it))
                    }
                }
            }))
        }
    }

    override suspend fun getInitialState(): MainState {
        return MainState.EmptyState()
    }

}