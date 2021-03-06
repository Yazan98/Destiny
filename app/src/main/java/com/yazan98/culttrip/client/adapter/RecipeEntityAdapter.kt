package com.yazan98.culttrip.client.adapter

import android.view.View
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.holders.RecipeEntityViewModel
import com.yazan98.culttrip.data.database.entity.RecipeEntity
import com.yazan98.culttrip.data.models.response.Recipe
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class RecipeEntityAdapter @Inject constructor(private val data: List<Recipe>): VortexBaseAdapter<RecipeEntityViewModel>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getLayoutRes(): Int {
       return R.layout.row_item
    }

    override fun getViewHolder(view: View): RecipeEntityViewModel {
        return RecipeEntityViewModel(view)
    }

    override fun onBindViewHolder(holder: RecipeEntityViewModel, position: Int) {
        holder.image?.let {
            data[position].image?.let { result ->
                VortexImageLoaders.loadLargeImageWithFresco(result, it, 200, 200)
            }
        }

        holder.name?.let {
            data[position].name?.let { result ->
                it.text = result
            }
        }

        holder.price?.let {
            data[position].price?.let { result ->
                it.text = "$result $"
            }
        }
    }

}
