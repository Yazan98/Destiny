package com.yazan98.culttrip.data.models.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Collection(
    var id: Long = 0,
    var image: String = "",
    var name: String = "",
    var exhibits: Long = 0,
    var popular: String
): Parcelable
