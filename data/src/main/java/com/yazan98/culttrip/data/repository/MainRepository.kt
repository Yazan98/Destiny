package com.yazan98.culttrip.data.repository

import com.yazan98.culttrip.data.CulttripRepository
import com.yazan98.culttrip.data.api.MainApi
import com.yazan98.culttrip.data.models.response.DestinyResponse
import com.yazan98.culttrip.data.models.response.Offer
import io.reactivex.Flowable
import javax.inject.Inject

class MainRepository @Inject constructor() : CulttripRepository<MainApi>() {

    override suspend fun getService(): MainApi {
        return serviceProvider.create(MainApi::class.java)
    }

    suspend fun getOffers(): Flowable<DestinyResponse<List<Offer>>> {
        return getService().getAllOffers()
    }

}
