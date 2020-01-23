package com.yazan98.culttrip.data


import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.prefs.VortexPrefsConfig
import io.vortex.android.prefs.VortexPrefsConsts
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@motif.Scope
interface RepositoryComponent {

    fun getRetrofitConfiguration(): Retrofit

    @motif.Objects
    open class RepositoryObjects {

        fun getRetrofitInstance(retrofitInterceptor: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(retrofitInterceptor)
                .build()
        }

        fun getRetrofitInterceptor(): OkHttpClient {
            val httpClient = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(interceptor)
            httpClient.addInterceptor { chain ->
                val request =
                    chain.request()
                        .newBuilder()
                        .addHeader("Authorization", VortexPrefsConfig.prefs.getString(VortexPrefsConsts.ACCESS_TOKEN_KEY, ""))
                        .build()
                chain.proceed(request)
            }
            return httpClient.build()
        }

    }
}