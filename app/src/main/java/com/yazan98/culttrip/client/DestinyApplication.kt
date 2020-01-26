package com.yazan98.culttrip.client

import android.content.Context
import com.intuit.sdp.BuildConfig
import com.yazan98.culttrip.client.utils.LeakUploader
import com.yazan98.culttrip.domain.ApplicationConsts
import com.yazan98.culttrip.domain.logic.AuthViewModel
import com.yazan98.culttrip.domain.logic.RecipeCommentsViewModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.vortex.android.keys.ImageLoader
import io.vortex.android.keys.LoggerType
import io.vortex.android.models.ui.VortexNotificationDetails
import io.vortex.android.prefs.VortexPrefsConfig
import io.vortex.android.ui.VortexMessageDelegation
import io.vortex.android.utils.VortexApplication
import io.vortex.android.utils.VortexConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import leakcanary.AppWatcher
import leakcanary.LeakCanary
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module
import timber.log.Timber
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DestinyApplication : VortexApplication() {

    private val messageController: VortexMessageDelegation by lazy {
        VortexMessageDelegation()
    }

    override fun onCreate() {
        super.onCreate()
        VortexPrefsConfig.prefs = getSharedPreferences(ApplicationConsts.SHARED_PREFS_NAME, Context.MODE_PRIVATE)

        GlobalScope.launch {
            VortexConfiguration
                .registerStrictMode()
                .registerCompatVector()
                .registerApplicationState(BuildConfig.DEBUG)
                .registerApplicationLogger(LoggerType.TIMBER)
                .registerApplicationClass(this@DestinyApplication)
                .registerImageLoader(ImageLoader.FRESCO)
                .registerLeakCanaryConfiguration()

            configNotifications()

            AppWatcher.config = AppWatcher.config.copy(watchFragmentViews = true)
            LeakCanary.config = LeakCanary.config.copy(onHeapAnalyzedListener = LeakUploader())

            try {
                applicationContext?.let {
                    Realm.init(it)
                    Realm.getInstance(setupRealmConfiguration())
                }
            } catch (ex: Exception) {
                Timber.e("Realm Error : ${ex.message}")
                handleDatabaseError(ex.message)
            }
        }

        startKoin {
            androidLogger(Level.DEBUG)
            modules(appModules)
        }
    }

    private val appModules: Module = module {
        viewModel<AuthViewModel> { AuthViewModel() }
        viewModel<RecipeCommentsViewModel> { RecipeCommentsViewModel() }
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

    private suspend fun setupRealmConfiguration() = suspendCoroutine<RealmConfiguration> {
        try {
            val config = RealmConfiguration.Builder()
                .name(ApplicationConsts.DATABASE_NAME)
                .schemaVersion(ApplicationConsts.DATABASE_VERSION)
                .inMemory()
                .build()
            it.resume(config)
        } catch (ex: Exception) {
            it.resumeWithException(ex)
        }
    }

    private suspend fun handleDatabaseError(message: String?) {
        withContext(Dispatchers.Main) {
            message?.let { result ->
                applicationContext?.let {
                    messageController.showLongMessage(result, it)
                }
            }
        }
    }

}
