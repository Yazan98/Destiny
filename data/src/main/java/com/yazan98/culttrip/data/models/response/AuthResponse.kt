package com.yazan98.culttrip.data.models.response

import android.os.Parcelable
import com.yazan98.culttrip.data.models.request.RegisterLocation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthResponse(
    var token: String = "",
    var user: Profile
): Parcelable

@Parcelize
data class Profile(
    var id: Long = 0L,
    var username: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var image: String = "",
    var enabled: Boolean,
    var accountStatus: String = "",
    var location: RegisterLocation
): Parcelable