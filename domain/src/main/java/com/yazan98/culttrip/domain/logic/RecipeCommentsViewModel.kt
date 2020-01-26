package com.yazan98.culttrip.domain.logic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yazan98.culttrip.data.di.RepositoriesComponentImpl
import com.yazan98.culttrip.data.models.request.CommentBody
import com.yazan98.culttrip.data.models.response.RecipeComment
import com.yazan98.culttrip.data.repository.RecipeRepository
import com.yazan98.culttrip.domain.action.CommentAction
import com.yazan98.culttrip.domain.state.CommentState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeCommentsViewModel @Inject constructor() :
    VortexViewModel<CommentState, CommentAction>() {

    private var recipeId: Long = 0
    val newCommentObserver: MutableLiveData<RecipeComment> by lazy { MutableLiveData<RecipeComment>() }
    private val repository: RecipeRepository by lazy {
        RepositoriesComponentImpl().getRecipesRepository()
    }

    override suspend fun execute(newAction: CommentAction) {
        withContext(Dispatchers.IO) {
            when (newAction) {
                is CommentAction.GetCommentsByRecipeId -> {
                    recipeId = newAction.get()
                    getAllComments(newAction.get())
                }
                is CommentAction.SubmitComment -> submitComment(
                    CommentBody(newAction.getId(), recipeId, newAction.getMessage())
                )
            }
        }
    }

    private suspend fun submitComment(body: CommentBody) {
        withContext(Dispatchers.IO) {
            addRxRequest(repository.submitComment(body).subscribe({
                viewModelScope.launch(Dispatchers.IO) {
                    it.data?.let {
                        newCommentObserver.postValue(it)
                    }
                }
            }, {
                viewModelScope.launch(Dispatchers.IO) {
                    it.message?.let {
                        acceptNewState(CommentState.ErrorState(it))
                    }
                }
            }))
        }
    }

    private suspend fun getAllComments(id: Long) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(repository.getCommentsByRecipeId(id).subscribe({
                viewModelScope.launch(Dispatchers.IO) {
                    it.data?.let {
                        acceptLoadingState(false)
                        acceptNewState(CommentState.SuccessState(it))
                    }
                }
            }, {
                viewModelScope.launch(Dispatchers.IO) {
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