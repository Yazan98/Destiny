package com.yazan98.culttrip.data.models.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Route(
    var id: Long = 0,
    var image: String = "",
    var name: String = "",
    var places: Long = 0
): Parcelable
