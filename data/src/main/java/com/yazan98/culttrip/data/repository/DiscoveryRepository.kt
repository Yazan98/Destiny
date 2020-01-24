package com.yazan98.culttrip.data.repository

import com.yazan98.culttrip.data.CulttripRepository
import com.yazan98.culttrip.data.api.DiscoveryApi
import com.yazan98.culttrip.data.models.response.Category
import com.yazan98.culttrip.data.models.response.DestinyResponse
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DiscoveryRepository @Inject constructor() : CulttripRepository<DiscoveryApi>() {

    override suspend fun getService(): DiscoveryApi {
        return withContext(Dispatchers.IO) {
            serviceProvider.create(DiscoveryApi::class.java)
        }
    }

    suspend fun getAllCategories(): Flowable<DestinyResponse<List<Category>>> {
        return getService().getAllCategories()
    }

//    suspend fun getAllRouts(): Flowable<DestinyResponse<List<Route>>> {
//        return getService().getAllRouts()
//    }

}