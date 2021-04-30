package com.example.firstmultiplatformproject.shared.datasource.network

import com.example.firstmultiplatformproject.shared.datasource.cache.Database
import com.example.firstmultiplatformproject.shared.datasource.cache.DriverFactory
import com.example.firstmultiplatformproject.shared.entity.RocketLaunch

class SpaceXSDK(driverFactory: DriverFactory) {
    private val database = Database(driverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class) suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }

}