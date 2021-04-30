package com.example.firstmultiplatformproject.android.di

import com.example.firstmultiplatformproject.android.presentation.BaseApplication
import com.example.firstmultiplatformproject.shared.cache.RecipeDatabase
import com.example.firstmultiplatformproject.shared.datasource.cache.Database
import com.example.firstmultiplatformproject.shared.datasource.cache.DriverFactory
import com.example.firstmultiplatformproject.shareddatasource.cache.model.RecipeEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideRecipeEntityMapper(): RecipeEntityMapper{
        return RecipeEntityMapper()
    }

    @Singleton
    @Provides
    fun provideRecipeDatabase(context: BaseApplication): RecipeDatabase {
        return Database( DriverFactory(context) ).createDatabase()
    }
}








