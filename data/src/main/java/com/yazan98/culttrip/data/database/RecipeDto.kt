package com.yazan98.culttrip.data.database

import androidx.lifecycle.LiveData
import com.yazan98.culttrip.data.database.entity.RecipeEntity
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Here i can just use Realm.getInstance()
 * but what about Vortex Application With Multi Product Flavors :D
 * each Flavor has Database and just send the database instance at the Constructor :D
 */
class RecipeDto(private val database: Realm) : DatabaseCrudOperations<RecipeEntity> {

    override suspend fun insertEntity(entity: RecipeEntity) {
        withContext(Dispatchers.IO) {
            database.executeTransactionAsync {
                it.insert(entity)
            }
        }
    }

    override suspend fun getEntityById(id: String): LiveData<RealmResults<RecipeEntity>> {
        return withContext(Dispatchers.IO) {
            database.where(RecipeEntity::class.java).equalTo("id", id)
                .findAllAsync().asLiveData()
        }
    }

    override suspend fun deleteEntityById(id: String) {
        withContext(Dispatchers.IO) {
            database.executeTransactionAsync {
                val result = it.where(RecipeEntity::class.java).equalTo("id", id).findFirst()
                result?.deleteFromRealm()
            }
        }
    }

    override suspend fun clearDatabase() {
        withContext(Dispatchers.IO) {
            database.executeTransactionAsync {
                val result = it.where(RecipeEntity::class.java).findAll()
                result.deleteAllFromRealm()
            }
        }
    }

    override suspend fun getAll(): RealmResults<RecipeEntity> {
        return withContext(Dispatchers.IO) {
            database.where(RecipeEntity::class.java)
                .findAll()
        }
    }

    override suspend fun isEmpty(): Boolean {
        return withContext(Dispatchers.IO) {
            database.isEmpty
        }
    }

}
