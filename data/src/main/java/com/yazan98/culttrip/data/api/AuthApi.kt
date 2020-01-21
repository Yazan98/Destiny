package com.yazan98.culttrip.data.api

import com.yazan98.culttrip.data.models.request.RegisterBody
import com.yazan98.culttrip.data.models.response.AuthResponse
import com.yazan98.culttrip.data.models.response.CulttripResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("accounts/register")
    fun registerAccount(@Body body: RegisterBody): Observable<CulttripResponse<AuthResponse>>

}
