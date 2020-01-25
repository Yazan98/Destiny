package com.yazan98.culttrip.data.models.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    var id: Long = 0,
    var name: String = "",
    var image: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var rating: Float = 0.0F,
    var votes: Long = 0,
    var fullDescription: String = "",
    var gms: String = "",
    var numberOfPieces: String = "",
    var popular: Boolean = true,
    var promoted: Boolean = false
): Parcelable
