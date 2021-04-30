package com.example.firstmultiplatformproject.sharednavigation

sealed class Screen(
    val route: String,
){
    object RecipeList: Screen("recipeList")

    object RecipeDetail: Screen("recipeDetail")
}