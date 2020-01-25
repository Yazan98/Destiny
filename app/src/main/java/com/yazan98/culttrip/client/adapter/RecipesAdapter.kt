package com.yazan98.culttrip.client.adapter

import android.view.View
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.holders.RecipesViewHolder
import com.yazan98.culttrip.client.adapter.listeners.RecipeListener
import com.yazan98.culttrip.data.models.response.Recipe
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class RecipesAdapter @Inject constructor(
    private val data: List<Recipe>,
    private var listener: RecipeListener? = null
) : VortexBaseAdapter<RecipesViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_recipe
    }

    override fun getViewHolder(view: View): RecipesViewHolder {
        return RecipesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.image?.let {
            data[position].image?.let { result ->
                VortexImageLoaders.loadLargeImageWithFresco(result, it, 500, 500)
            }
        }

        holder.name?.let {
            data[position].name.let { result ->
                it.text = result
            }
        }

        holder.description?.let {
            data[position].description.let { result ->
                it.text = result
            }
        }

        holder.price?.let {
            data[position].price?.let { result ->
                it.text = "$$result"
            }
        }

        holder?.item?.let {
            it.setOnClickListener {
                listener?.onRecipeClicked(data[position].id)
            }
        }
    }

    fun des() {
        listener = null
    }
}
