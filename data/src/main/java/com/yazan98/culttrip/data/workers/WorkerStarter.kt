package com.yazan98.culttrip.data.workers

import android.content.Context
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


object WorkerStarter {

    suspend fun startOffers(context: Context) {
        withContext(Dispatchers.IO) {
            val constraint: Constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest =
                PeriodicWorkRequest.Builder(OffersWorker::class.java, 15, TimeUnit.MINUTES)
                    .setConstraints(constraint)
                    .build()

            val workManager = WorkManager.getInstance(context)
            workManager.enqueueUniquePeriodicWork(
                "OffersWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }
    }
}