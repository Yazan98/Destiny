package com.yazan98.culttrip.domain.action

import io.vortex.android.VortexAction

open class CategoryAction : VortexAction {

    class GetRecipesByCategoryId(private val id: Long): CategoryAction() {
        fun get() = id
    }

}
