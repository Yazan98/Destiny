package com.yazan98.culttrip.data.models.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    var id: Long = 0,
    var name: String = "",
    var icon: String = ""
): Parcelable
