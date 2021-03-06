package com.yazan98.culttrip.data.api

import com.yazan98.culttrip.data.models.request.CommentBody
import com.yazan98.culttrip.data.models.response.DestinyResponse
import com.yazan98.culttrip.data.models.response.Recipe
import com.yazan98.culttrip.data.models.response.RecipeComment
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeApi {

    @GET("recipes/{id}/comments")
    fun getAllCommentsByRecipeId(@Path("id") id: Long): Flowable<DestinyResponse<List<RecipeComment>>>

    @GET("recipes/{id}")
    fun getRecipeInfoById(@Path("id")id: Long): Flowable<DestinyResponse<Recipe>>

    @POST("recipes/comment")
    fun submitComment(@Body body: CommentBody): Flowable<DestinyResponse<RecipeComment>>

}
