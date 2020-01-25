package com.yazan98.culttrip.data.repository

import com.yazan98.culttrip.data.CulttripRepository
import com.yazan98.culttrip.data.api.CategoryApi
import com.yazan98.culttrip.data.models.response.Category
import com.yazan98.culttrip.data.models.response.DestinyResponse
import com.yazan98.culttrip.data.models.response.Recipe
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepository @Inject constructor() : CulttripRepository<CategoryApi>() {

    override suspend fun getService(): CategoryApi {
        return withContext(Dispatchers.IO) {
            serviceProvider.create(CategoryApi::class.java)
        }
    }

    suspend fun getAllRecipesByCategoryId(id: Long): Flowable<DestinyResponse<List<Recipe>>> {
        return getService().getAllRecipesByCategoryId(id)
    }

}