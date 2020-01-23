package com.yazan98.culttrip.data.api

import com.yazan98.culttrip.data.models.response.Category
import com.yazan98.culttrip.data.models.response.CulttripResponse
import com.yazan98.culttrip.data.models.response.Route
import io.reactivex.Flowable
import retrofit2.http.GET

interface DiscoveryApi {

    @GET("categories")
    fun getAllCategories(): Flowable<CulttripResponse<List<Category>>>

    @GET("routs/all")
    fun getAllRouts(): Flowable<CulttripResponse<List<Route>>>

}
