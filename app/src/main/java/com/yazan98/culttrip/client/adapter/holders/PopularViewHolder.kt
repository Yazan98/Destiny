package com.yazan98.culttrip.client.adapter.holders

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_route.view.*
import javax.inject.Inject

class PopularViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val name: TextView? = view.NameRecipe
    val raing: RatingBar? = view.Rating
    val price: TextView? = view.PriceRecipe
    val image: SimpleDraweeView? = view.simpleDraweeView
}