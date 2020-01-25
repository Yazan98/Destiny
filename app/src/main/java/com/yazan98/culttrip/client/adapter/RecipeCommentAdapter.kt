package com.yazan98.culttrip.client.adapter

import android.view.View
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.holders.RecipeCommentViewHolder
import com.yazan98.culttrip.data.models.response.RecipeComment
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class RecipeCommentAdapter @Inject constructor(private val data: List<RecipeComment>) :
    VortexBaseAdapter<RecipeCommentViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_comment
    }

    override fun getViewHolder(view: View): RecipeCommentViewHolder {
        return RecipeCommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeCommentViewHolder, position: Int) {
        holder.comment?.let {
            data[position].comment?.let { result ->
                it.text = result
            }
        }

        holder.image?.let {
            data[position].profile.image?.let { result ->
                VortexImageLoaders.loadLargeImageWithFresco(result, it, 200, 200)
            }
        }

        holder.name?.let {
            data[position].profile.username?.let { result ->
                it.text = result
            }
        }
    }

}
