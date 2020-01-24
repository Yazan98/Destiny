package com.yazan98.culttrip.data.api

import com.yazan98.culttrip.data.models.response.Category
import com.yazan98.culttrip.data.models.response.DestinyResponse
import io.reactivex.Flowable
import retrofit2.http.GET

interface DiscoveryApi {

    @GET("categories")
    fun getAllCategories(): Flowable<DestinyResponse<List<Category>>>
//
//    @GET("routs/all")
//    fun getAllOffers(): Flowable<DestinyResponse<List<Offer>>>

}
