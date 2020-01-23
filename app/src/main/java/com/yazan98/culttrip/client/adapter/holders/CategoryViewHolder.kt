package com.yazan98.culttrip.client.adapter.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_category.view.*
import javax.inject.Inject

class CategoryViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val image: SimpleDraweeView? = view.CategoryImage
    val name: TextView? = view.TitleCategory
}
