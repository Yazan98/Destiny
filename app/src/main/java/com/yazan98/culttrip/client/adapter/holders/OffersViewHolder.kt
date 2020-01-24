package com.yazan98.culttrip.client.adapter.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_collection.view.*
import javax.inject.Inject

class OffersViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val backgroundImage: SimpleDraweeView? = view.BackgroundImage
    val discount: TextView? =  view.DiscountOffer
    val title: TextView? = view.OfferTitle
}
