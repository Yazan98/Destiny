package com.yazan98.culttrip.client.adapter.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_item.view.*
import javax.inject.Inject

class RecipeEntityViewModel @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val image: SimpleDraweeView? = view.simpleDraweeView4
    val name: TextView? = view.textView11
    val price: TextView? = view.PriceRecipe
}