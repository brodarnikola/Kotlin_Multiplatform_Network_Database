package com.example.firstmultiplatformproject.shareddomain.model

import kotlinx.datetime.LocalDate

/**
 * See Recipe example: https://food2fork.ca/
 */
data class Recipe (
    val id: Int,
    val title: String,
    val publisher: String,
    val featuredImage: String,
    val rating: Int,
    val sourceUrl: String,
    val ingredients: List<String> = listOf(),
    val dateAdded: LocalDate,
    val dateUpdated: LocalDate,
)

