package com.yazan98.culttrip.client.adapter

import android.view.View
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.holders.PopularViewHolder
import com.yazan98.culttrip.client.adapter.listeners.RecipeListener
import com.yazan98.culttrip.data.models.response.Recipe
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class PopularRecipesAdapter @Inject constructor(
    private val data: List<Recipe>,
    private var listener: RecipeListener? = null
) : VortexBaseAdapter<PopularViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_route
    }

    override fun getViewHolder(view: View): PopularViewHolder {
        return PopularViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.image?.let {
            data[position].image.let { result ->
                VortexImageLoaders.loadLargeImageWithFresco(result, it, 800, 400)
            }
        }

        holder.name?.let {
            data[position].name.let { result ->
                it.text = result
            }
        }

        holder.raing?.let {
            data[position].rating?.let { result ->
                it.rating = result
            }
        }

        holder.price?.let {
            data[position].price?.let { result ->
                it.text = "$$result"
            }
        }

        holder.item?.let {
            it.setOnClickListener {
                listener?.onRecipeClicked(data[position].id)
            }
        }

    }

    fun destroy() {
        listener = null
    }
}