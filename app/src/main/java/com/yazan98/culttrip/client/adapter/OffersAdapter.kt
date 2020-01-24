package com.yazan98.culttrip.client.adapter

import android.view.View
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.holders.OffersViewHolder
import com.yazan98.culttrip.data.models.response.Offer
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class OffersAdapter @Inject constructor(private val data: List<Offer>) : VortexBaseAdapter<OffersViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_collection
    }

    override fun getViewHolder(view: View): OffersViewHolder {
        return OffersViewHolder(view)
    }

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {
        holder?.let { container ->
            data[position].image.let { image ->
                container.backgroundImage?.let {
                    VortexImageLoaders.loadLargeImageWithFresco(image, it, 500, 500)
                }
            }

            data[position].name?.let { result ->
                container.title?.let {
                    it.text = result
                }
            }

            data[position].discount?.let { result ->
                container.discount?.let {
                    it.text = "$result %OFF"
                }
            }

        }
    }

}
