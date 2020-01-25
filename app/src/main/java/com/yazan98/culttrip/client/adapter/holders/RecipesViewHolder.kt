package com.yazan98.culttrip.client.adapter.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_recipe.view.*
import javax.inject.Inject

class RecipesViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val image: SimpleDraweeView? = view.RecipeImage
    val name: TextView? = view.textView2
    val description: TextView? = view.DesRecipe
    val price: TextView? = view.PriceRecipe
    val item: View? = view.RecipeItem
}
