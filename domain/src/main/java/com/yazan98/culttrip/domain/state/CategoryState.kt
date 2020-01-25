package com.yazan98.culttrip.domain.state

import com.yazan98.culttrip.data.models.response.Recipe
import io.vortex.android.state.VortexState

open class CategoryState : VortexState {

    class EmptyState : CategoryState()
    class SuccessState(
        private val popularRecipes: List<Recipe>,
        private val promotedRecipes: List<Recipe>
    ) : CategoryState() {
        fun getPopularRecipes() = popularRecipes
        fun getPromotedRecipes() = promotedRecipes
    }

    class ErrorState(private val message: String) : CategoryState() {
        fun get() = message
    }

}
