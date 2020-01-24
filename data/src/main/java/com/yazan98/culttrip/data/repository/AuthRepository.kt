package com.yazan98.culttrip.data.repository

import com.yazan98.culttrip.data.CulttripRepository
import com.yazan98.culttrip.data.api.AuthApi
import com.yazan98.culttrip.data.models.request.RegisterBody
import com.yazan98.culttrip.data.models.response.AuthResponse
import com.yazan98.culttrip.data.models.response.DestinyResponse
import io.reactivex.Observable
import javax.inject.Inject

class AuthRepository @Inject constructor() : CulttripRepository<AuthApi>() {

    override suspend fun getService(): AuthApi {
        return serviceProvider.create(AuthApi::class.java)
    }

    suspend fun registerAccount(body: RegisterBody): Observable<DestinyResponse<AuthResponse>> {
        return getService().registerAccount(body)
    }

}