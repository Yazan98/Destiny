package com.yazan98.culttrip.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yazan98.culttrip.data.models.request.OfferBody
import com.yazan98.culttrip.data.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class OffersWorker @Inject constructor(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    private val notificationsRepository: NotificationRepository by lazy {
        NotificationRepository()
    }

    override suspend fun doWork(): Result = coroutineScope {
        notificationsRepository.getService()
            .sendOffer(OfferBody("From Work Manager", "The Schedual Each Second"))
            .subscribe()
        Result.success()
    }

}
