package com.yazan98.culttrip.client.fragment.reg

import android.view.View
import androidx.navigation.findNavController
import com.yazan98.culttrip.client.R
import io.vortex.android.ui.fragment.VortexBaseFragment
import kotlinx.android.synthetic.main.fragment_on_boarding.*
import javax.inject.Inject

class OnBoardingFragment @Inject constructor() : VortexBaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_on_boarding
    }

    override fun initScreen(view: View) {
        BoardingIcon?.apply {
            this.setImageURI("res:/${R.drawable.logo}")
        }

        ContinueButton?.apply {
            this.setOnClickListener {
                findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
            }
        }
    }

}
