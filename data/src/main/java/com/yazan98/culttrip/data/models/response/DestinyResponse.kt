package com.yazan98.culttrip.data.models.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class DestinyResponse<T>(
    var code: Int = 0,
    var message: String = "",
    var status: String = "",
    var data: T
)
