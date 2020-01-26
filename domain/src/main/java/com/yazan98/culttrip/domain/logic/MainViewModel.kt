package com.yazan98.culttrip.domain.logic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yazan98.culttrip.data.di.RepositoriesComponentImpl
import com.yazan98.culttrip.data.models.response.Category
import com.yazan98.culttrip.data.models.response.Recipe
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

    val categories: MutableLiveData<List<Category>> by lazy { MutableLiveData<List<Category>>() }
    val recipes: MutableLiveData<List<Recipe>> by lazy { MutableLiveData<List<Recipe>>() }
    private val repository: MainRepository by lazy {
        RepositoriesComponentImpl().getCollectionRepository()
    }

    override suspend fun execute(newAction: MainAction) {
        withContext(Dispatchers.IO) {
            if (getStateHandler().value is MainState.ErrorState || getStateHandler().value == null) {
                when (newAction) {
                    is MainAction.GetMainPageDetails -> {
                        getAllOffers()
                        getAllCategories()
                        getRecipes()
                    }
                }
            }
        }
    }

    private suspend fun getAllCategories() {
        withContext(Dispatchers.IO) {
            addRxRequest(repository.getCategories().subscribe({
                viewModelScope.launch(Dispatchers.IO) {
                    categories.postValue(it.data)
                }
            }, {
                viewModelScope.launch(Dispatchers.IO) {
                    it.message?.let {
                        acceptLoadingState(false)
                        acceptNewState(MainState.ErrorState(it))
                    }
                }
            }))
        }
    }

    private suspend fun getAllOffers() {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(repository.getOffers().subscribe({
                viewModelScope.launch(Dispatchers.IO) {
                    it.data?.let {
                        acceptLoadingState(false)
                        acceptNewState(MainState.SuccessState(it))
                    }
                }
            }, {
                viewModelScope.launch(Dispatchers.IO) {
                    it.message?.let {
                        acceptLoadingState(false)
                        acceptNewState(MainState.ErrorState(it))
                    }
                }
            }))
        }
    }

    private suspend fun getRecipes() {
        withContext(Dispatchers.IO) {
            addRxRequest(repository.getRecipes().subscribe({
                viewModelScope.launch(Dispatchers.IO) {
                    it.data?.let {
                        recipes.postValue(it)
                    }
                }
            }, {
                viewModelScope.launch(Dispatchers.IO) {
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