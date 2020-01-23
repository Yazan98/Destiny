package com.yazan98.culttrip.client.adapter

import android.view.View
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.holders.CategoryViewHolder
import com.yazan98.culttrip.data.models.response.Category
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class CategoryAdapter @Inject constructor(private val data: List<Category>): VortexBaseAdapter<CategoryViewHolder>() {
    override fun getItemCount(): Int {
        return data.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_category
    }

    override fun getViewHolder(view: View): CategoryViewHolder {
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.image?.let { item ->
            data[position].icon?.let { result ->
                VortexImageLoaders.loadLargeImageWithFresco(result, item, 300, 300)
            }
        }

        holder.name?.let { item ->
            data[position].name.let {
                item.text = it
            }
        }

    }

}
