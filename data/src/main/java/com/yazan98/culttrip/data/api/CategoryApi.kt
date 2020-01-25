package com.yazan98.culttrip.data.api

import com.yazan98.culttrip.data.models.response.Category
import com.yazan98.culttrip.data.models.response.DestinyResponse
import com.yazan98.culttrip.data.models.response.Recipe
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApi {

    @GET("recipes/category/{id}")
    fun getAllRecipesByCategoryId(@Path("id") id: Long): Flowable<DestinyResponse<List<Recipe>>>

}
