package com.yazan98.culttrip.data.repository

import com.yazan98.culttrip.data.DestinyRepository
import com.yazan98.culttrip.data.api.RecipeApi
import com.yazan98.culttrip.data.models.response.DestinyResponse
import com.yazan98.culttrip.data.models.response.Recipe
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

}