package com.example.firstmultiplatformproject.shared.datasource.cache

import android.content.Context
import com.example.firstmultiplatformproject.shared.cache.RecipeDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(RecipeDatabase.Schema, context, "test.db")
    }
}