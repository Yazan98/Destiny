package com.yazan98.culttrip.domain.logic

import com.yazan98.culttrip.data.di.RepositoriesComponentImpl
import com.yazan98.culttrip.data.models.request.RegisterBody
import com.yazan98.culttrip.data.repository.AuthRepository
import com.yazan98.culttrip.domain.action.AuthAction
import com.yazan98.culttrip.domain.state.AuthState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthViewModel @Inject constructor() : VortexViewModel<AuthState, AuthAction>() {

    private val authRepository: AuthRepository by lazy {
        RepositoriesComponentImpl().getAuthRepository()
    }

    override suspend fun execute(newAction: AuthAction) {
        withContext(Dispatchers.IO) {
            if (getStateHandler().value == null || getStateHandler().value is AuthState.ErrorResponse) {
                when (newAction) {
                    is AuthAction.LoginAction -> loginAccount(
                        newAction.getEmail(),
                        newAction.getPassword()
                    )
                    is AuthAction.RegisterAction -> registerAccount(newAction.get())
                }
            }
        }
    }

    private suspend fun registerAccount(body: RegisterBody) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(authRepository.registerAccount(body).subscribe({
                it?.let {
                    GlobalScope.launch {
                        handleStateWithLoading(AuthState.AuthSuccessResponse(it.data))
                    }
                }
            }, {
                it?.let {
                    it.message?.let {
                        GlobalScope.launch {
                            acceptNewState(AuthState.ErrorResponse(it))
                            acceptLoadingState(false)
                        }
                    }
                }
            }))
        }
    }

    private suspend fun loginAccount(email: String, password: String) {
        withContext(Dispatchers.IO) {

        }
    }

    override suspend fun getInitialState(): AuthState {
        return AuthState.EmptyState()
    }

}
