package com.yazan98.culttrip.domain.logic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yazan98.culttrip.data.database.RecipeDto
import com.yazan98.culttrip.data.di.RepositoriesComponentImpl
import com.yazan98.culttrip.data.repository.CategoryRepository
import com.yazan98.culttrip.domain.action.CategoryAction
import com.yazan98.culttrip.domain.state.CategoryState
import io.realm.Realm
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryViewModel @Inject constructor() : VortexViewModel<CategoryState, CategoryAction>() {

    val databaseObserver: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    private val discoveryRepository: CategoryRepository by lazy {
        RepositoriesComponentImpl().getDiscoveryRepository()
    }

    override suspend fun execute(newAction: CategoryAction) {
        withContext(Dispatchers.IO) {
            if (getStateHandler().value == null || getStateHandler().value is CategoryState.ErrorState) {
                when (newAction) {
                    is CategoryAction.GetRecipesByCategoryId -> getRecipesByCategoryId(newAction.get())
                }
            }

            if (newAction is CategoryAction.CartCheckAction) {
                checkDatabaseStatus()
            }
        }
    }

    private suspend fun getRecipesByCategoryId(id: Long) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(discoveryRepository.getAllRecipesByCategoryId(id).subscribe({
                viewModelScope.launch(Dispatchers.IO) {
                    it.data.let {
                        acceptNewState(
                            CategoryState.SuccessState(
                                it.filter { s -> s.popular },
                                it.filter { s -> !s.popular }
                            )
                        )
                        acceptLoadingState(false)
                    }
                }
            }, {
                viewModelScope.launch(Dispatchers.IO) {
                    it.message?.let {
                        acceptLoadingState(false)
                        acceptNewState(CategoryState.ErrorState(it))
                    }
                }
            }))
        }
    }

    private suspend fun checkDatabaseStatus() {
        withContext(Dispatchers.IO) {
            RecipeDto(Realm.getDefaultInstance()).isEmpty().let {
                databaseObserver.postValue(it)
            }
        }
    }

    override suspend fun getInitialState(): CategoryState {
        return CategoryState.EmptyState()
    }

    override fun onCleared() {
        super.onCleared()
        Realm.getDefaultInstance().close()
    }

}
