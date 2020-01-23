package com.yazan98.culttrip.client.adapter.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_route.view.*
import javax.inject.Inject

class RouteViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val name: TextView? = view.RouteName
    val places: TextView? = view.RoutePlaces
    val image: SimpleDraweeView? = view.simpleDraweeView
}