package com.example.firstmultiplatformproject.sharedinteractors.recipe_list

import com.example.firstmultiplatformproject.shared.cache.RecipeDatabase
import com.example.firstmultiplatformproject.shared.datasource.cache.Database
import com.example.firstmultiplatformproject.shareddatasource.cache.model.RecipeEntityMapper
import com.example.firstmultiplatformproject.shareddomain.data.DataState
import com.example.firstmultiplatformproject.shareddomain.model.Recipe
import com.example.firstmultiplatformproject.shareddomain.util.CommonFlow
import com.example.firstmultiplatformproject.shareddomain.util.DateUtil
import com.example.firstmultiplatformproject.shareddomain.util.asCommonFlow
import com.example.firstmultiplatformproject.shareddatasource.network.RecipeService
import com.example.firstmultiplatformproject.shareddatasource.network.model.RecipeDtoMapper
import com.example.firstmultiplatformproject.sharedutil.RECIPE_PAGINATION_PAGE_SIZE
import comexamplefirstmultiplatformprojectsharedcache.Recipe_Entity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class SearchRecipes(
    private val recipeService: RecipeService,
    private val dtoMapper: RecipeDtoMapper,
    private val recipeDatabase: RecipeDatabase,
    private val recipeEntityMapper: RecipeEntityMapper,
    private val dateUtil: DateUtil,
) {

    @Throws(Exception::class)
    fun execute(
        token: String,
        page: Int,
        query: String,
    ): CommonFlow<DataState<List<Recipe>>> = flow  {
        try{
            emit(DataState.loading())

            // just to show pagination, api is fast
            delay(1000)

            // force error for testing
            if (query == "error") {
                throw Exception("Forcing an error... Search FAILED!")
            }

            // Convert: NetworkRecipeEntity -> Recipe -> RecipeCacheEntity
            val recipes: List<Recipe> = getRecipesFromNetwork(
                token = token,
                page = page,
                query = query,
            )

            // insert into cache
            val queries = recipeDatabase.appDatabaseQueries
            val entities = recipeEntityMapper.toEntityList(recipes)

            for(entity in entities){
                queries.insertRecipe(
                    id = entity.id,
                    title = entity.title,
                    publisher = entity.publisher,
                    featured_image = entity.featuredImage,
                    rating = entity.rating,
                    source_url = entity.sourceUrl,
                    ingredients = entity.ingredients,
                    date_added = entity.dateAdded,
                    date_updated = entity.dateUpdated
                )
            }

            // query the cache
            val cacheResult = if (query.isBlank()) {
                queries.getAllRecipes(
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE.toLong(),
                    page = page.toLong()
                )
            } else {
                queries.searchRecipes(
                    query = query,
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE.toLong(),
                    page = page.toLong()
                )
            }.executeAsList()

             // emit List<Recipe> from cache
            // Must manually map this since Recipe_Entity object is generated by SQL Delight
            val list: ArrayList<Recipe> = ArrayList()
            for(entity in cacheResult){
                list.add(Recipe(
                    id = entity.id.toInt(),
                    title = entity.title,
                    publisher = entity.publisher,
                    featuredImage = entity.featured_image,
                    rating = entity.rating.toInt(),
                    sourceUrl = entity.source_url,
                    ingredients = recipeEntityMapper.convertIngredientsToList(entity.ingredients),
                    dateAdded = dateUtil.toLocalDate(entity.date_added),
                    dateUpdated = dateUtil.toLocalDate(entity.date_updated)
                ))
            }

            emit(DataState.success(list))
        } catch (e: Exception) {
            val listRecipes = getDataFromLocalDatabase(query, page)
            if( listRecipes.isNotEmpty() ) {
                val list: ArrayList<Recipe> = ArrayList()
                for(entity in listRecipes){
                    list.add(Recipe(
                        id = entity.id.toInt(),
                        title = entity.title,
                        publisher = entity.publisher,
                        featuredImage = entity.featured_image,
                        rating = entity.rating.toInt(),
                        sourceUrl = entity.source_url,
                        ingredients = recipeEntityMapper.convertIngredientsToList(entity.ingredients),
                        dateAdded = dateUtil.toLocalDate(entity.date_added),
                        dateUpdated = dateUtil.toLocalDate(entity.date_updated)
                    ))
                }
                emit(DataState.success(list))
                emit(DataState.error<List<Recipe>>("Check your internet connection. Display old data from local database. Error: ${e.message}"))
            }
            else {
                emit(DataState.error<List<Recipe>>(e.message ?: "Unknown Error"))
            }
        }
    }.asCommonFlow()

    private fun getDataFromLocalDatabase(query: String, page: Int) : List<Recipe_Entity> {

        val queries = recipeDatabase.appDatabaseQueries
        // query the cache
        return if (query.isBlank()) {
            queries.getAllRecipes(
                pageSize = RECIPE_PAGINATION_PAGE_SIZE.toLong(),
                page = page.toLong()
            )
        } else {
            queries.searchRecipes(
                query = query,
                pageSize = RECIPE_PAGINATION_PAGE_SIZE.toLong(),
                page = page.toLong()
            )
        }.executeAsList()
    }

    private suspend fun getRecipesFromNetwork(
        token: String,
        page: Int,
        query: String
    ): List<Recipe> {
        return dtoMapper.toDomainList(
            recipeService.search(
                token = token,
                page = page,
                query = query,
            ).results
        )
    }

}








