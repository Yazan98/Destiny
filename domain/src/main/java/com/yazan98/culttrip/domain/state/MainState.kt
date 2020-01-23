package com.yazan98.culttrip.domain.state

import com.yazan98.culttrip.data.models.response.Collection
import io.vortex.android.state.VortexState

open class MainState : VortexState {
    class EmptyState : MainState()
    class ErrorState(private val message: String): MainState() {
        fun get() = message
    }
    class SuccessState(private val response: List<Collection>): MainState() {
        fun get() = response
    }

}
