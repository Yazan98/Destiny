package com.yazan98.culttrip.data.di

import com.yazan98.culttrip.data.repository.AuthRepository
import com.yazan98.culttrip.data.repository.MainRepository
import com.yazan98.culttrip.data.repository.CategoryRepository
import com.yazan98.culttrip.data.repository.RecipeRepository

@motif.Scope
interface RepositoriesComponent {

    fun getAuthRepository(): AuthRepository

    fun getCollectionRepository(): MainRepository

    fun getDiscoveryRepository(): CategoryRepository

    fun getRecipesRepository(): RecipeRepository

    @motif.Objects
    open class Objects {

        fun getAuth(): AuthRepository {
            return AuthRepository()
        }

        fun getCollections(): MainRepository {
            return MainRepository()
        }

        fun getDiscoveryRepo(): CategoryRepository {
            return CategoryRepository()
        }

        fun getRecipe(): RecipeRepository {
            return RecipeRepository()
        }
    }

}