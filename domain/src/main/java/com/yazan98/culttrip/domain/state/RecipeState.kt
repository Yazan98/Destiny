package com.yazan98.culttrip.domain.state

import com.yazan98.culttrip.data.models.response.Recipe
import io.vortex.android.state.VortexState

open class RecipeState : VortexState {

    class EmptyState : RecipeState()
    class SuccessState(private val response: Recipe) : RecipeState() {
        fun get() = response
    }

    class ErrorState(private val message: String) : RecipeState() {
        fun get() = message
    }
}