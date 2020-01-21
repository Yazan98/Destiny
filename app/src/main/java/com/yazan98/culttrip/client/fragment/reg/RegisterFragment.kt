package com.yazan98.culttrip.client.fragment.reg

import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.screen.MainScreen
import com.yazan98.culttrip.data.models.request.RegisterBody
import com.yazan98.culttrip.data.models.request.RegisterLocation
import com.yazan98.culttrip.data.models.response.AuthResponse
import com.yazan98.culttrip.domain.ApplicationConsts
import com.yazan98.culttrip.domain.action.AuthAction
import com.yazan98.culttrip.domain.logic.AuthViewModel
import com.yazan98.culttrip.domain.state.AuthState
import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.ui.fragment.VortexLceFragment
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import javax.inject.Inject

class RegisterFragment @Inject constructor() :
    VortexLceFragment<AuthState, AuthAction, AuthViewModel>() {

    private val viewModel: AuthViewModel by viewModel<AuthViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_register
    }

    override suspend fun getController(): AuthViewModel {
        return viewModel
    }

    override fun initScreen(view: View) {
        RegisterIcon?.apply {
            this.setActualImageResource(R.drawable.screen_icon)
        }

        RegisterButtonMain?.apply {
            this.setOnClickListener {
                GlobalScope.launch {
                    when {
                        EmailField?.text.toString().trim().isEmpty() -> showMessage(getString(R.string.email_required))
                        PasswordField?.text.toString().trim().isEmpty() -> showMessage(getString(R.string.pass_required))
                        NameField?.text.toString().trim().isEmpty() -> showMessage(getString(R.string.name_required))
                        PhoneNumberField?.text.toString().trim().isEmpty() -> showMessage(
                            getString(
                                R.string.phone_required
                            )
                        )
                        LocationField?.text.toString().trim().isEmpty() -> showMessage(getString(R.string.location_required))
                        else -> {
                            getController().execute(
                                AuthAction.RegisterAction(
                                    RegisterBody(
                                        NameField?.text.toString().trim(),
                                        PasswordField?.text.toString().trim(),
                                        EmailField?.text.toString().trim(),
                                        "image-url-here",
                                        PhoneNumberField?.text.toString().trim(),
                                        RegisterLocation(name = LocationField?.text.toString().trim())
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }

    }

    override suspend fun onStateChanged(newState: AuthState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is AuthState.AuthSuccessResponse -> successResponse(newState.get())
                is AuthState.ErrorResponse -> showMessage(newState.get())
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
                        GlobalScope.launch {
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

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.IO) {
            onLoadingChanged(newState)
        }
    }

    override suspend fun onLoadingChanged(status: Boolean) {
        withContext(Dispatchers.Main) {
            when (status) {
                true -> {
                    RegisterLoading?.showView()
                    RegisterContainer?.goneView()
                }

                false -> {
                    RegisterLoading?.goneView()
                    RegisterContainer?.showView()
                }
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
}
