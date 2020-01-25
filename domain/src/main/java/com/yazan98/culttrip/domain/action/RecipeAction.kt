package com.yazan98.culttrip.domain.action

import io.vortex.android.VortexAction

open class RecipeAction : VortexAction {

    class GetRecipeById(private val id: Long) : RecipeAction() {
        fun get() = id
    }

    class AddPrice : RecipeAction()
    class AddToDatabase : RecipeAction()

}