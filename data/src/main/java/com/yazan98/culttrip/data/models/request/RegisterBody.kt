package com.yazan98.culttrip.data.models.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterBody(
    var name: String = "",
    var password: String = "",
    var email: String = "",
    var image: String = "",
    var phoneNumber: String = "",
    var location: RegisterLocation,
    var enabled: Boolean = true
): Parcelable

@Parcelize
data class RegisterLocation(
    var name: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
): Parcelable
