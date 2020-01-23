package com.yazan98.culttrip.client.screen

import android.os.Bundle
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.utils.CulttripViewPagerAdapter
import io.vortex.android.ui.activity.VortexScreen
import kotlinx.android.synthetic.main.screen_main.*

class MainScreen : VortexScreen() {
    override fun getLayoutRes(): Int {
        return R.layout.screen_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager?.let { manager ->
            viewpager?.let {
                it.adapter = CulttripViewPagerAdapter(manager)
            }
        }
    }
}
