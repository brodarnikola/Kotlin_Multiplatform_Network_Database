package com.example.firstmultiplatformproject.android.di

import com.example.firstmultiplatformproject.shared.cache.RecipeDatabase
import com.example.firstmultiplatformproject.shareddatasource.cache.model.RecipeEntityMapper
import com.example.firstmultiplatformproject.shareddatasource.network.RecipeService
import com.example.firstmultiplatformproject.shareddatasource.network.model.RecipeDtoMapper
import com.example.firstmultiplatformproject.shareddomain.util.DateUtil
import com.example.firstmultiplatformproject.sharedinteractors.recipe.GetRecipe
import com.example.firstmultiplatformproject.sharedinteractors.recipe_list.RestoreRecipes
import com.example.firstmultiplatformproject.sharedinteractors.recipe_list.SearchRecipes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun provideSearchRecipes(
        recipeService: RecipeService,
        dtoMapper: RecipeDtoMapper,
        entityMapper: RecipeEntityMapper,
        recipeDatabase: RecipeDatabase,
        dateUtil: DateUtil
    ): SearchRecipes {
        return SearchRecipes(
            recipeService = recipeService,
            dtoMapper = dtoMapper,
            recipeEntityMapper = entityMapper,
            recipeDatabase = recipeDatabase,
            dateUtil = dateUtil
        )
    }

    @Singleton
    @Provides
    fun providerestoreRecipes(
        entityMapper: RecipeEntityMapper,
        recipeDatabase: RecipeDatabase,
        dateUtil: DateUtil
    ): RestoreRecipes {
        return RestoreRecipes(
            entityMapper = entityMapper,
            recipeDatabase = recipeDatabase,
            dateUtil = dateUtil
        )
    }

    @Singleton
    @Provides
    fun provideGetRecipe(
        recipeDatabase: RecipeDatabase,
        entityMapper: RecipeEntityMapper,
        dateUtil: DateUtil
    ): GetRecipe {
        return GetRecipe(
            recipeDatabase = recipeDatabase,
            recipeEntityMapper = entityMapper,
            dateUtil = dateUtil
        )
    }
}









