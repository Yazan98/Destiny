package com.yazan98.culttrip.data.api

import com.yazan98.culttrip.data.models.response.Category
import com.yazan98.culttrip.data.models.response.DestinyResponse
import com.yazan98.culttrip.data.models.response.Offer
import com.yazan98.culttrip.data.models.response.Recipe
import io.reactivex.Flowable
import retrofit2.http.GET

interface MainApi {

    @GET("offers")
    fun getAllOffers(): Flowable<DestinyResponse<List<Offer>>>

    @GET("categories")
    fun getAllCategories(): Flowable<DestinyResponse<List<Category>>>

    @GET("foods")
    fun getAllRecipes(): Flowable<DestinyResponse<List<Recipe>>>

}
