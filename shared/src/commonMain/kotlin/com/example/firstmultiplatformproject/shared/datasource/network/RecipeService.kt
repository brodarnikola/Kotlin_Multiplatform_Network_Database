package com.example.firstmultiplatformproject.shareddatasource.network

import com.example.firstmultiplatformproject.shareddatasource.network.model.RecipeDto
import com.example.firstmultiplatformproject.shareddatasource.network.model.RecipeSearchResponse

interface RecipeService {

    suspend fun get(token: String, id: Int): RecipeDto

    suspend fun search(token: String, page: Int, query: String): RecipeSearchResponse
}