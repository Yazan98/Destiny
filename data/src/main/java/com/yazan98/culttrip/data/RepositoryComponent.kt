package com.yazan98.culttrip.data


import okhttp3.OkHttpClient
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
            return httpClient.build()
        }

    }
}