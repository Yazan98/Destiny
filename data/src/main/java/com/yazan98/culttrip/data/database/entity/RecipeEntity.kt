package com.yazan98.culttrip.data.database.entity

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.*
import java.util.*

@RealmClass(name = "RecipeEntity", fieldNamingPolicy = RealmNamingPolicy.PASCAL_CASE)
open class RecipeEntity : RealmObject() , RealmModel {

    @PrimaryKey
    @RealmField(name = "id")
    var id: String = UUID.randomUUID().toString()

    @Required
    @RealmField(name = "name")
    var name: String = ""

    @RealmField(name = "price")
    var price: Double = 0.0

    @RealmField(name = "qty")
    var qty: Int = 0

    @Required
    @RealmField(name = "image")
    var image: String = ""

}
