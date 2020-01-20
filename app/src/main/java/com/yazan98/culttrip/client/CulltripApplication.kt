package com.yazan98.culttrip.client

import android.content.Context
import com.intuit.sdp.BuildConfig
import com.yazan98.culttrip.client.utils.LeakUploader
import com.yazan98.culttrip.domain.ApplicationConsts
import io.vortex.android.keys.ImageLoader
import io.vortex.android.keys.LoggerType
import io.vortex.android.models.ui.VortexNotificationDetails
import io.vortex.android.prefs.VortexPrefsConfig
import io.vortex.android.utils.VortexApplication
import io.vortex.android.utils.VortexConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import leakcanary.AppWatcher
import leakcanary.LeakCanary
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module

class CulltripApplication : VortexApplication() {

    override fun onCreate() {
        super.onCreate()

        GlobalScope.launch {
            VortexConfiguration
                .registerStrictMode()
                .registerCompatVector()
                .registerApplicationState(BuildConfig.DEBUG)
                .registerApplicationLogger(LoggerType.TIMBER)
                .registerApplicationClass(this@CulltripApplication)
                .registerImageLoader(ImageLoader.FRESCO)
                .registerLeakCanaryConfiguration()

            VortexPrefsConfig.prefs = getSharedPreferences(ApplicationConsts.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            configNotifications()

            AppWatcher.config = AppWatcher.config.copy(watchFragmentViews = true)
            LeakCanary.config = LeakCanary.config.copy(
                onHeapAnalyzedListener = LeakUploader()
            )

        }

        startKoin {
            androidLogger(Level.DEBUG)
            modules(appModules)
        }
    }

    private val appModules: Module = module {

    }

    private suspend fun configNotifications() {
        withContext(Dispatchers.IO) {
            applicationContext?.let {
                //TODO(Yazan): "Change Values Later"
                notificationsController.createMultiNotificationChannels(
                    arrayListOf(
                        VortexNotificationDetails("Offers", "Offers Channel", "fdsgd15d3fg1"),
                        VortexNotificationDetails("Offers", "Offers Channel", "fdsgd15d3fg1"),
                        VortexNotificationDetails("Offers", "Offers Channel", "fdsgd15d3fg1")
                    ),
                    it
                )
            }
        }
    }

}