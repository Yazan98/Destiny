package com.yazan98.culttrip.domain.logic

import androidx.lifecycle.ViewModel
import com.yazan98.culttrip.data.di.RepositoriesComponentImpl
import com.yazan98.culttrip.data.repository.RecipeRepository
import com.yazan98.culttrip.domain.action.CommentAction
import com.yazan98.culttrip.domain.state.CommentState
import com.yazan98.culttrip.domain.state.MainState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeCommentsViewModel @Inject constructor(): VortexViewModel<CommentState, CommentAction>() {

    private val repository: RecipeRepository by lazy {
        RepositoriesComponentImpl().getRecipesRepository()
    }

    override suspend fun execute(newAction: CommentAction) {
        withContext(Dispatchers.IO) {
            when (newAction) {
                is CommentAction.GetCommentsByRecipeId -> getAllComments(newAction.get())
            }
        }
    }

    private suspend fun getAllComments(id: Long) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(repository.getCommentsByRecipeId(id).subscribe({
                GlobalScope.launch {
                    it.data?.let {
                        acceptLoadingState(false)
                        acceptNewState(CommentState.SuccessState(it))
                    }
                }
            }, {
                GlobalScope.launch {
                    it.message?.let {
                        acceptLoadingState(false)
                        acceptNewState(CommentState.ErrorState(it))
                    }
                }
            }))
        }
    }

    override suspend fun getInitialState(): CommentState {
        return CommentState.EmptyState()
    }

}