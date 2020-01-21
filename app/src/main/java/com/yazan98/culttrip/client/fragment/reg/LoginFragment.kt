package com.yazan98.culttrip.client.fragment.reg

import android.view.View
import androidx.navigation.fragment.findNavController
import com.yazan98.culttrip.client.R
import io.vortex.android.ui.fragment.VortexBaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginFragment @Inject constructor() : VortexBaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun initScreen(view: View) {
        LoginIcon?.apply {
            this.setActualImageResource(R.drawable.screen_icon)
        }

        LoginButton?.apply {
            this.setOnClickListener {
                GlobalScope.launch {
                    when {
                        EmailField?.text.toString().trim().isEmpty() -> showMessage(getString(R.string.email_required))
                        PasswordField?.text.toString().trim().isEmpty() -> showMessage(getString(R.string.pass_required))
                        else -> {

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
                messageController.showSnackbarWithColor(it , message, R.color.colorPrimary)
            }
        }
    }

}
