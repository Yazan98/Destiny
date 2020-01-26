package com.yazan98.culttrip.domain.logic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yazan98.culttrip.data.database.RecipeDto
import com.yazan98.culttrip.data.database.entity.RecipeEntity
import com.yazan98.culttrip.data.di.RepositoriesComponentImpl
import com.yazan98.culttrip.data.repository.RecipeRepository
import com.yazan98.culttrip.domain.action.RecipeAction
import com.yazan98.culttrip.domain.state.RecipeState
import io.realm.Realm
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeViewModel @Inject constructor() : VortexViewModel<RecipeState, RecipeAction>() {

    val portions: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val editedPrice: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }

    private val repository: RecipeRepository by lazy {
        RepositoriesComponentImpl().getRecipesRepository()
    }

    override suspend fun execute(newAction: RecipeAction) {
        withContext(Dispatchers.IO) {
            when (newAction) {
                is RecipeAction.GetRecipeById -> getAllRecipes(newAction.get())
                is RecipeAction.AddPrice -> addPrice()
                is RecipeAction.AddToDatabase -> addToDatabase()
            }
        }
    }

    private suspend fun addPrice() {
        withContext(Dispatchers.IO) {
            getStateHandler().value?.let { basePrice ->
                val price = (basePrice as RecipeState.SuccessState).get().price
                editedPrice.postValue(editedPrice.value?.plus(price))
                portions.postValue(portions.value?.plus(1))
            }
        }
    }

    private suspend fun getAllRecipes(id: Long) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(repository.getRecipeById(id).subscribe({
                viewModelScope.launch(Dispatchers.IO) {
                    it.data?.let {
                        acceptLoadingState(false)
                        editedPrice.postValue(it.price)
                        portions.postValue(1)
                        acceptNewState(RecipeState.SuccessState(it))
                    }
                }
            }, {
                viewModelScope.launch(Dispatchers.IO) {
                    it.message?.let {
                        acceptLoadingState(false)
                        acceptNewState(RecipeState.ErrorState(it))
                    }
                }
            }))
        }
    }

    private suspend fun addToDatabase() {
        withContext(Dispatchers.IO) {
            getStateHandler().value?.let {
                (it as RecipeState.SuccessState).get().let {
                    portions.value?.let { qty ->
                        val newEntity = RecipeEntity()
                        newEntity.image = it.image
                        newEntity.name = it.name
                        newEntity.price = it.price
                        newEntity.qty = qty
                        RecipeDto(Realm.getDefaultInstance()).insertEntity(newEntity)
                    }
                }
            }
        }
    }

    override suspend fun getInitialState(): RecipeState {
        return RecipeState.EmptyState()
    }

}
