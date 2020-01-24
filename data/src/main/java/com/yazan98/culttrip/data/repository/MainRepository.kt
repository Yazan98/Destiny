package com.yazan98.culttrip.data.repository

import com.yazan98.culttrip.data.CulttripRepository
import com.yazan98.culttrip.data.api.MainApi
import com.yazan98.culttrip.data.models.response.Category
import com.yazan98.culttrip.data.models.response.DestinyResponse
import com.yazan98.culttrip.data.models.response.Offer
import com.yazan98.culttrip.data.models.response.Recipe
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor() : CulttripRepository<MainApi>() {

    override suspend fun getService(): MainApi {
        return withContext(Dispatchers.IO) {
            serviceProvider.create(MainApi::class.java)
        }
    }

    suspend fun getOffers(): Flowable<DestinyResponse<List<Offer>>> {
        return getService().getAllOffers()
    }

    suspend fun getCategories(): Flowable<DestinyResponse<List<Category>>> {
        return getService().getAllCategories()
    }

    suspend fun getRecipes(): Flowable<DestinyResponse<List<Recipe>>> {
        return getService().getAllRecipes()
    }

}
