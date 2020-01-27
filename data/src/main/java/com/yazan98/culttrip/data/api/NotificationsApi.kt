package com.yazan98.culttrip.data.api

import com.yazan98.culttrip.data.models.request.OfferBody
import com.yazan98.culttrip.data.models.response.DestinyResponse
import com.yazan98.culttrip.data.models.response.NotificationResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationsApi {

    @POST("notifications/offer")
    fun sendOffer(@Body body: OfferBody): Single<DestinyResponse<NotificationResponse>>

}