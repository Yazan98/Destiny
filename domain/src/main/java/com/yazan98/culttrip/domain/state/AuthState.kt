package com.yazan98.culttrip.domain.state

import com.yazan98.culttrip.data.models.response.AuthResponse
import io.vortex.android.state.VortexState

open class AuthState : VortexState {

    class EmptyState: AuthState()
    class AuthSuccessResponse(private val response: AuthResponse): AuthState() {
        fun get() = response
    }

    class ErrorResponse(private val message: String): AuthState() {
        fun get() = message
    }


}
