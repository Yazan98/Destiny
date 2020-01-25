package com.yazan98.culttrip.domain.logic

import com.yazan98.culttrip.data.RepositoriesComponentImpl
import com.yazan98.culttrip.data.repository.RecipeRepository
import com.yazan98.culttrip.domain.action.RecipeAction
import com.yazan98.culttrip.domain.state.RecipeState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeViewModel @Inject constructor() : VortexViewModel<RecipeState, RecipeAction>() {

    private val repository: RecipeRepository by lazy {
        RepositoriesComponentImpl().getRecipesRepository()
    }

    override suspend fun execute(newAction: RecipeAction) {
        withContext(Dispatchers.IO) {
            getAllRecipes((newAction as RecipeAction.GetRecipeById).get())
        }
    }

    private suspend fun getAllRecipes(id: Long) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(repository.getRecipeById(id).subscribe({
                GlobalScope.launch {
                    it.data?.let {
                        acceptLoadingState(false)
                        acceptNewState(RecipeState.SuccessState(it))
                    }
                }
            }, {
                GlobalScope.launch {
                    it.message?.let {
                        acceptLoadingState(false)
                        acceptNewState(RecipeState.ErrorState(it))
                    }
                }
            }))
        }
    }

    override suspend fun getInitialState(): RecipeState {
        return RecipeState.EmptyState()
    }

}