package com.yazan98.culttrip.data.repository

import com.yazan98.culttrip.data.DestinyRepository
import com.yazan98.culttrip.data.api.NotificationsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotificationRepository @Inject constructor() : DestinyRepository<NotificationsApi>() {
    public override suspend fun getService(): NotificationsApi {
        return withContext(Dispatchers.IO) {
            serviceProvider.create(NotificationsApi::class.java)
        }
    }
}