package com.yazan98.culttrip.client.adapter

import android.view.View
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.adapter.holders.CollectionViewHolder
import com.yazan98.culttrip.data.models.response.Collection
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class CollectionsAdapter @Inject constructor(private val data: List<Collection>) :
    VortexBaseAdapter<CollectionViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_collection
    }

    override fun getViewHolder(view: View): CollectionViewHolder {
        return CollectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder?.let { container ->
            data[position].image.let { image ->
                container.backgroundImage?.let {
                    VortexImageLoaders.loadLargeImageWithFresco(image, it, 500, 500)
                }
            }

            data[position].popular.let { result ->
                container.des?.let {
                    if (result.equals("POPULAR")) {
                        it.text = context.getString(R.string.featured)
                    } else {
                        it.text = context.getString(R.string.recommend)
                    }
                }
            }

            data[position].name?.let { result ->
                container.title?.let {
                    it.text = result
                }
            }

        }
    }

}
