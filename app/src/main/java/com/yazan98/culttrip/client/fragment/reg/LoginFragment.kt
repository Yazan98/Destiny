package com.yazan98.culttrip.client.fragment.reg

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.screen.MainScreen
import com.yazan98.culttrip.data.models.response.AuthResponse
import com.yazan98.culttrip.domain.ApplicationConsts
import com.yazan98.culttrip.domain.action.AuthAction
import com.yazan98.culttrip.domain.logic.AuthViewModel
import com.yazan98.culttrip.domain.state.AuthState
import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import javax.inject.Inject

class LoginFragment @Inject constructor() : VortexFragment<AuthState, AuthAction, AuthViewModel>() {

    private val viewModel: AuthViewModel by viewModel<AuthViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override suspend fun getController(): AuthViewModel {
        return viewModel
    }

    override fun initScreen(view: View) {
        LoginIcon?.apply {
            this.setActualImageResource(R.drawable.screen_icon)
        }

        LoginButton?.apply {
            this.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    when {
                        EmailField?.text.toString().trim().isEmpty() -> showMessage(getString(R.string.email_required))
                        PasswordField?.text.toString().trim().isEmpty() -> showMessage(getString(R.string.pass_required))
                        else -> {
                            getController().execute(
                                AuthAction.LoginAction(
                                    EmailField?.text.toString().trim(),
                                    PasswordField?.text.toString().trim()
                                )
                            )
                        }
                    }
                }
            }
        }

        RegisterButton?.apply {
            this.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

    }

    private suspend fun showMessage(message: String) {
        withContext(Dispatchers.Main) {
            activity?.let {
                messageController.showSnackbarWithColor(it, message, R.color.colorPrimary)
            }
        }
    }


    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when (newState) {
                true -> {
                    LayoutOne?.goneView()
                    textInputLayout?.goneView()
                    LoginButton?.goneView()
                    RegisterButton?.goneView()
                    LoadingLogin?.showView()
                }

                false -> {
                    LayoutOne?.showView()
                    textInputLayout?.showView()
                    LoginButton?.showView()
                    RegisterButton?.showView()
                    LoadingLogin?.goneView()
                }
            }
        }
    }

    override suspend fun onStateChanged(newState: AuthState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is AuthState.AuthSuccessResponse -> successResponse(newState.get())
                is AuthState.ErrorResponse -> showMessageCustom(newState.get())
            }
        }
    }

    private suspend fun showMessageCustom(message: String) {
        withContext(Dispatchers.Main) {
            activity?.let {
                messageController.showSnackbarWithColor(it, message, R.color.colorPrimary)
            }
        }
    }

    private suspend fun successResponse(response: AuthResponse) {
        withContext(Dispatchers.Main) {
            activity?.let {
                MaterialAlertDialogBuilder(it)
                    .setTitle(getString(R.string.register_completed))
                    .setMessage("Hi Mr, ${response.user.username} Your Account Created Successfully , Welcome To Culttrip Client")
                    .setPositiveButton("Ok") { dialog, which ->
                        viewLifecycleOwner.lifecycleScope.launch {
                            saveKeys(response)
                            startScreen<MainScreen>(true)
                        }
                        dialog.dismiss()
                    }
                    .show();
            }
        }
    }

    private suspend fun saveKeys(response: AuthResponse) {
        withContext(Dispatchers.IO) {
            VortexPrefs.saveUserStatus(true)
            VortexPrefs.saveAccessToken(response.token)
            VortexPrefs.put(ApplicationConsts.ID, response.user.id)
        }
    }

}
