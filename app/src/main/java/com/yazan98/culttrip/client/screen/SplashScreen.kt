package com.yazan98.culttrip.client.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yazan98.culttrip.client.R
import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.ui.activity.VortexScreen
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashScreen : VortexScreen() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            VortexPrefs.getUserStatus()?.let {
                when (it) {
                    true -> startScreen<MainScreen>(true)
                    false -> startScreen<RegisterScreen>(true)
                }
            }
        }
    }

}
