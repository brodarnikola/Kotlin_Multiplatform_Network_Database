package com.example.firstmultiplatformproject.sharedpresentation.ui.recipe

sealed class RecipeEvent{

    data class GetRecipeEvent(
        val id: Int
    ): RecipeEvent()

}