package com.yazan98.culttrip.client.fragment.operations

import android.view.View
import com.yazan98.culttrip.client.R
import io.vortex.android.ui.fragment.VortexBaseFragment
import io.vortex.android.utils.random.VortexImageLoaders
import kotlinx.android.synthetic.main.fragment_recipe.*
import javax.inject.Inject

class RecipeFragment @Inject constructor() : VortexBaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_recipe
    }

    override fun initScreen(view: View) {
        simpleDraweeView2?.let {
            VortexImageLoaders.loadLargeImageWithFresco(
                "https://images.unsplash.com/photo-1558030006-450675393462?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1789&q=80",
                it,
                800,
                900
            )
        }
    }

}
