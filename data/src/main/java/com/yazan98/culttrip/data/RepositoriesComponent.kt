package com.yazan98.culttrip.data

import com.yazan98.culttrip.data.repository.AuthRepository

@motif.Scope
interface RepositoriesComponent {

    fun getAuthRepository(): AuthRepository

    @motif.Objects
    open class Objects {
        fun getAuth(): AuthRepository {
            return AuthRepository()
        }
    }

}