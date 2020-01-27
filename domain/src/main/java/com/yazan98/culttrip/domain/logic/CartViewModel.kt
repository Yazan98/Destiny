package com.yazan98.culttrip.domain.logic

import com.yazan98.culttrip.data.database.RecipeDto
import com.yazan98.culttrip.data.database.entity.RecipeEntity
import com.yazan98.culttrip.data.models.response.Recipe
import com.yazan98.culttrip.domain.action.CartAction
import com.yazan98.culttrip.domain.state.CartState
import io.realm.Realm
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CartViewModel @Inject constructor() : VortexViewModel<CartState, CartAction>() {

    override suspend fun execute(newAction: CartAction) {
        withContext(Dispatchers.IO) {
            getAllItems()
        }
    }

    private suspend fun getAllItems() {
        withContext(Dispatchers.IO) {
            RecipeDto(Realm.getDefaultInstance()).getAll()?.let {
                acceptNewState(CartState.SuccessState(getRecipes(it)))
            }
        }
    }

    private suspend fun getRecipes(response: List<RecipeEntity>) = suspendCoroutine<List<Recipe>> {
        val result = ArrayList<Recipe>()
        response.forEach {
            result.add(
                Recipe(
                    name = it.name,
                    image = it.image,
                    price = it.price
                )
            )
        }
        it.resume(result)
    }

    override suspend fun getInitialState(): CartState {
        return CartState.EmptyState()
    }

}
