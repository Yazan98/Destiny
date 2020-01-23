package com.yazan98.culttrip.data

import com.yazan98.culttrip.data.repository.AuthRepository
import com.yazan98.culttrip.data.repository.CollectionRepository
import com.yazan98.culttrip.data.repository.DiscoveryRepository

@motif.Scope
interface RepositoriesComponent {

    fun getAuthRepository(): AuthRepository

    fun getCollectionRepository(): CollectionRepository

    fun getDiscoveryRepository(): DiscoveryRepository

    @motif.Objects
    open class Objects {

        fun getAuth(): AuthRepository {
            return AuthRepository()
        }

        fun getCollections(): CollectionRepository {
            return CollectionRepository()
        }

        fun getDiscoveryRepo(): DiscoveryRepository {
            return DiscoveryRepository()
        }
    }

}