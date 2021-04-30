package com.example.firstmultiplatformproject.shared.datasource.cache

import com.squareup.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun createDriver(): SqlDriver
}