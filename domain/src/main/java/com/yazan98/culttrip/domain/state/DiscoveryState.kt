package com.yazan98.culttrip.domain.state

import com.yazan98.culttrip.data.models.response.Category
import io.vortex.android.state.VortexState

open class DiscoveryState : VortexState {

    class EmptyState : DiscoveryState()
    class SuccessState(private val response: List<Category>) : DiscoveryState() {
        fun get() = response
    }

    class ErrorState(private val message: String) : DiscoveryState() {
        fun get() = message
    }

}
