package com.example.firstmultiplatformproject.shared.datasource.cache

import com.example.firstmultiplatformproject.shared.cache.RecipeDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(RecipeDatabase.Schema, "test.db")
    }
}