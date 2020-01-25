package com.yazan98.culttrip.data.database

import androidx.lifecycle.MutableLiveData
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults
import javax.inject.Inject

fun <T : RealmModel> RealmResults<T>.asLiveData() = RealmLiveData<T>(this)
class RealmLiveData<T : RealmModel> @Inject constructor(private val results: RealmResults<T>) : MutableLiveData<RealmResults<T>>() {

    private val listener: RealmChangeListener<RealmResults<T>> =
        RealmChangeListener<RealmResults<T>> { results ->
            setValue(results)
        }

    override fun onActive() {
        results.addChangeListener(listener)
    }

    override fun onInactive() {
        results.removeChangeListener(listener);
    }

}