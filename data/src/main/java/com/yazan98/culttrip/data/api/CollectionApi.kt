package com.yazan98.culttrip.data.api

import com.yazan98.culttrip.data.models.response.CulttripResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import com.yazan98.culttrip.data.models.response.Collection

interface CollectionApi {

    @GET("collections/all")
    fun getCollections(): Flowable<CulttripResponse<List<Collection>>>

}
