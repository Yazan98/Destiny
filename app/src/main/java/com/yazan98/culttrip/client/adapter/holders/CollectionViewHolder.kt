package com.yazan98.culttrip.client.adapter.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_collection.view.*
import javax.inject.Inject

class CollectionViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val backgroundImage: SimpleDraweeView? = view.BackgroundImage
}
