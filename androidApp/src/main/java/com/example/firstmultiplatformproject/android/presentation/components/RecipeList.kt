package com.example.firstmultiplatformproject.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firstmultiplatformproject.android.presentation.ui.hilt_compose_recipe.BatteryStatus
import com.example.firstmultiplatformproject.android.presentation.ui.hilt_compose_recipe.HiltComposeRecipeActivity
import com.example.firstmultiplatformproject.shareddomain.model.Recipe
import com.example.firstmultiplatformproject.sharednavigation.Screen
import com.example.firstmultiplatformproject.sharedutil.RECIPE_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerNextPage: () -> Unit,
    onNavigateToRecipeDetailScreen: (String) -> Unit,
    context: HiltComposeRecipeActivity
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
    ) {
        if (loading && recipes.isEmpty()) {
            LoadingRecipeListShimmer(imageHeight = 250.dp)
        } else if (recipes.isEmpty()) {
            NothingHere()
        } else {
            LazyColumn {
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    onChangeScrollPosition(index)
                    if ((index + 1) >= (page * RECIPE_PAGINATION_PAGE_SIZE) && !loading) {
                        onTriggerNextPage()
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            val route = Screen.RecipeDetail.route + "/${recipe.id}"
                            onNavigateToRecipeDetailScreen(route)

                            //example of using classic navigation betweeen activities with Intent
//                            val intent = Intent( context, MainActivity::class.java )
//                            context.startActivity(intent)
//                            context.finish()
                        }
                    )
                }
            }
        }
        BatteryStatus(context)
    }


}
