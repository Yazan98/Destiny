package com.yazan98.culttrip.client.adapter.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_comment.view.*
import javax.inject.Inject

class RecipeCommentViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val image: SimpleDraweeView? = view.simpleDraweeView3
    val name: TextView? = view.textView8
    val comment: TextView? = view.RecipeComment
}
