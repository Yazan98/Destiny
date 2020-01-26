package com.yazan98.culttrip.data.repository

import com.yazan98.culttrip.data.DestinyRepository
import com.yazan98.culttrip.data.api.RecipeApi
import com.yazan98.culttrip.data.models.request.CommentBody
import com.yazan98.culttrip.data.models.response.DestinyResponse
import com.yazan98.culttrip.data.models.response.Recipe
import com.yazan98.culttrip.data.models.response.RecipeComment
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepository @Inject constructor() : DestinyRepository<RecipeApi>() {

    override suspend fun getService(): RecipeApi {
        return withContext(Dispatchers.IO) {
            serviceProvider.create(RecipeApi::class.java)
        }
    }

    suspend fun getRecipeById(id: Long): Flowable<DestinyResponse<Recipe>> {
        return getService().getRecipeInfoById(id)
    }

    suspend fun getCommentsByRecipeId(id: Long): Flowable<DestinyResponse<List<RecipeComment>>> {
        return getService().getAllCommentsByRecipeId(id)
    }

    suspend fun submitComment(body: CommentBody): Flowable<DestinyResponse<RecipeComment>> {
        return getService().submitComment(body)
    }

}