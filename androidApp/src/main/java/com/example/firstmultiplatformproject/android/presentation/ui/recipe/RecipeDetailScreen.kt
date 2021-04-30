package com.example.firstmultiplatformproject.android.presentation.ui.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firstmultiplatformproject.android.presentation.components.IMAGE_HEIGHT
import com.example.firstmultiplatformproject.android.presentation.components.LoadingRecipeShimmer
import com.example.firstmultiplatformproject.android.presentation.components.RecipeView
import com.example.firstmultiplatformproject.android.presentation.theme.AppTheme
import com.example.firstmultiplatformproject.sharedpresentation.ui.recipe.RecipeEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun RecipeDetailScreen(
  isDarkTheme: Boolean,
  recipeId: Int?,
  viewModel: RecipeViewModel,
){
  if (recipeId == null){
    Text("Invalid Recipe")
  }else {
    // fire a one-off event to get the recipe from api
    val onLoad = viewModel.onLoad.value
    if (!onLoad) {
      viewModel.onLoad.value = true
      viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
    }

    val loading = viewModel.loading.value

    val recipe = viewModel.recipe.value

    val scaffoldState = rememberScaffoldState()

    val dialogQueue = viewModel.dialogQueue

    AppTheme(
      displayProgressBar = loading,
      scaffoldState = scaffoldState,
      darkTheme = isDarkTheme,
      dialogQueue = dialogQueue.queue.value,
    ){
      Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
          scaffoldState.snackbarHostState
        }
      ) {
        Box (
          modifier = Modifier.fillMaxSize()
        ){
          if (loading && recipe == null) {
            LoadingRecipeShimmer(imageHeight = IMAGE_HEIGHT.dp)
          }
          else if(!loading && recipe == null && onLoad){
            Text("Invalid Recipe")
          }
          else {
            recipe?.let {RecipeView(recipe = it) }
          }
        }
      }
    }
  }
}