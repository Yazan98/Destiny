package com.yazan98.culttrip.domain.action

import com.yazan98.culttrip.data.models.request.RegisterBody
import io.vortex.android.VortexAction

open class AuthAction : VortexAction {

    class LoginAction(private val email: String, private val password: String): AuthAction() {
        fun getEmail() = email
        fun getPassword() = password
    }

    class RegisterAction(private val body: RegisterBody): AuthAction() {
        fun get() = body
    }

}
