package com.yazan98.culttrip.client.adapter

import android.view.View
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.holders.RouteViewHolder
import com.yazan98.culttrip.data.models.response.Route
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class RoutsAdapter @Inject constructor(private val data: List<Route>) : VortexBaseAdapter<RouteViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_route
    }

    override fun getViewHolder(view: View): RouteViewHolder {
        return RouteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
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

        holder.places?.let {
            data[position].places.let { result ->
                it.text = "${result} ${context.getString(R.string.places)}"
            }
        }
    }
}