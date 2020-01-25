package com.yazan98.culttrip.data.models.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class RecipeComment(
    var id: Long = 0,
    var comment: String = "",
    var profile: Profile,
    var recipeId: Long
): Parcelable
