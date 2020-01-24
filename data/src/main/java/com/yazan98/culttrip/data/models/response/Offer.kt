package com.yazan98.culttrip.data.models.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Offer(
    var id: Long = 0,
    var name: String = "",
    var image: String = "",
    var discount: Double = 0.0
): Parcelable
