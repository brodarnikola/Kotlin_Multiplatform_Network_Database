package com.example.firstmultiplatformproject.sharedinteractors.recipe

import com.example.firstmultiplatformproject.shared.cache.RecipeDatabase
import com.example.firstmultiplatformproject.shareddatasource.cache.model.RecipeEntityMapper
import com.example.firstmultiplatformproject.shareddomain.data.DataState
import com.example.firstmultiplatformproject.shareddomain.model.Recipe
import com.example.firstmultiplatformproject.shareddomain.util.DateUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Retrieve a recipe from the cache given it's unique id.
 */
class GetRecipe (
  private val recipeDatabase: RecipeDatabase,
  private val recipeEntityMapper: RecipeEntityMapper,
  private val dateUtil: DateUtil,
){

  fun execute(
    recipeId: Int,
  ): Flow<DataState<Recipe>> = flow {
    try {
      emit(DataState.loading())

      // just to show loading, cache is fast
      delay(1000)

      val recipe = getRecipeFromCache(recipeId = recipeId)

      emit(DataState.success(recipe))

    }catch (e: Exception){
      emit(DataState.error<Recipe>(e.message?: "Unknown Error"))
    }
  }

  private fun getRecipeFromCache(recipeId: Int): Recipe {
    return recipeDatabase.appDatabaseQueries.getRecipeById(recipeId.toLong()).executeAsOne().let { entity ->
      Recipe(
        id = entity.id.toInt(),
        title = entity.title,
        publisher = entity.publisher,
        featuredImage = entity.featured_image,
        rating = entity.rating.toInt(),
        sourceUrl = entity.source_url,
        ingredients = recipeEntityMapper.convertIngredientsToList(entity.ingredients),
        dateAdded = dateUtil.toLocalDate(entity.date_added),
        dateUpdated = dateUtil.toLocalDate(entity.date_updated)
      )
    }
  }

}