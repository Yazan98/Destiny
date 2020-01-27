package com.yazan98.culttrip.domain.state

import com.yazan98.culttrip.data.models.response.Recipe
import io.vortex.android.state.VortexState

open class CartState : VortexState {

    class EmptyState : CartState()
    class SuccessState(private val response: List<Recipe>) : CartState() {
        fun get() = response
    }

    class ErrorResponse(private val message: String) : CartState() {
        fun get() = message
    }

}
