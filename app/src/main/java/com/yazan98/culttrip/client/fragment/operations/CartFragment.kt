package com.yazan98.culttrip.client.fragment.operations

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.RecipeEntityAdapter
import com.yazan98.culttrip.domain.action.CartAction
import com.yazan98.culttrip.domain.logic.CartViewModel
import com.yazan98.culttrip.domain.state.CartState
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.ui.linearVerticalLayout
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartFragment @Inject constructor() : VortexFragment<CartState, CartAction, CartViewModel>() {

    private lateinit var viewModel: CartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        }
    }

    override suspend fun getController(): CartViewModel {
        return withContext(Dispatchers.IO) {
            viewModel
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_cart
    }

    override suspend fun getLoadingState(newState: Boolean) = Unit
    override fun initScreen(view: View) {
        lifecycleScope.launch(Dispatchers.IO) {
            getController().execute(CartAction.GetCartItems())
        }

        lifecycleScope.launch(Dispatchers.Main) {
            getController().getStateHandler().observe(this@CartFragment, Observer {
               lifecycleScope.launch {
                   onStateChanged(it)
               }
            })
        }

        CheckoutButton?.apply {
            this.setOnClickListener {
                lifecycleScope.launch {
                    activity?.let {
                        messageController.showSnackbarWithColor(it, getString(R.string.not_allowed), R.color.colorPrimary)
                    }
                }
            }
        }
    }

    override suspend fun onStateChanged(newState: CartState) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            println("The Data Acc")
            (newState as CartState.SuccessState).let { response ->
                activity?.let {
                    CartItemsRecycler?.apply {
                        this.linearVerticalLayout(it)
                        this.adapter = RecipeEntityAdapter(response.get())
                        (this.adapter as RecipeEntityAdapter).context = it
                    }
                }
            }
        }
    }

}
