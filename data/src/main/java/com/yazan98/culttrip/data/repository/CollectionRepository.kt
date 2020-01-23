package com.yazan98.culttrip.data.repository

import com.yazan98.culttrip.data.CulttripRepository
import com.yazan98.culttrip.data.api.CollectionApi
import com.yazan98.culttrip.data.models.response.CulttripResponse
import io.reactivex.Flowable
import javax.inject.Inject
import com.yazan98.culttrip.data.models.response.Collection

class CollectionRepository @Inject constructor() : CulttripRepository<CollectionApi>() {

    override suspend fun getService(): CollectionApi {
        return serviceProvider.create(CollectionApi::class.java)
    }

    suspend fun getCollections(): Flowable<CulttripResponse<List<Collection>>> {
        return getService().getCollections()
    }

}