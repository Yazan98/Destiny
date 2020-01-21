package com.yazan98.culttrip.data

import io.vortex.android.data.VortexRepository
import io.vortex.android.models.VortexRequestDetailsProvider

abstract class CulttripRepository<Api> : VortexRepository<Api>() {

    private val provider: RepositoryComponent by lazy {
        RepositoryComponentImpl()
    }

    init {
        this.serviceProvider = provider.getRetrofitConfiguration()
    }


    override fun getBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    override fun getRequestDetails(): VortexRequestDetailsProvider {
        // This is for Vortex Retrofit Interceptor and no need to implement the Interceptor at this Application
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}