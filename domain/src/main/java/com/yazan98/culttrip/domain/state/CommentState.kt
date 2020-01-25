package com.yazan98.culttrip.domain.state

import com.yazan98.culttrip.data.models.response.RecipeComment
import io.vortex.android.state.VortexState

open class CommentState : VortexState {
    class EmptyState : CommentState()
    class SuccessState(private val response: List<RecipeComment>) : CommentState() {
        fun get() = response
    }

    class ErrorState(private val message: String) : CommentState() {
        fun get() = message
    }
}