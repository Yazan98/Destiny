package com.yazan98.culttrip.data

import com.yazan98.culttrip.data.repository.AuthRepository
import com.yazan98.culttrip.data.repository.CollectionRepository

@motif.Scope
interface RepositoriesComponent {

    fun getAuthRepository(): AuthRepository

    fun getCollectionRepository(): CollectionRepository

    @motif.Objects
    open class Objects {

        fun getAuth(): AuthRepository {
            return AuthRepository()
        }

        fun getCollections(): CollectionRepository {
            return CollectionRepository()
        }
    }

}