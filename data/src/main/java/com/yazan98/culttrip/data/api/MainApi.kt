package com.yazan98.culttrip.data.api

import com.yazan98.culttrip.data.models.response.Category
import com.yazan98.culttrip.data.models.response.DestinyResponse
import com.yazan98.culttrip.data.models.response.Offer
import com.yazan98.culttrip.data.models.response.Recipe
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET

interface MainApi {

    @GET("offers")
    fun getAllOffers(): Single<DestinyResponse<List<Offer>>>

    @GET("categories")
    fun getAllCategories(): Single<DestinyResponse<List<Category>>>

    @GET("foods")
    fun getAllRecipes(): Single<DestinyResponse<List<Recipe>>>

}
