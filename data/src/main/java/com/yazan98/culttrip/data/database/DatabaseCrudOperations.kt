package com.yazan98.culttrip.data.database


import androidx.lifecycle.LiveData
import io.realm.RealmObject
import io.realm.RealmResults

interface DatabaseCrudOperations<T: RealmObject> {

    suspend fun insertEntity(entity: T)

    suspend fun getEntityById(id: String): LiveData<RealmResults<T>>

    suspend fun deleteEntityById(id: String)

    suspend fun clearDatabase()

    suspend fun getAll(): RealmResults<T>

    suspend fun isEmpty(): Boolean

}
