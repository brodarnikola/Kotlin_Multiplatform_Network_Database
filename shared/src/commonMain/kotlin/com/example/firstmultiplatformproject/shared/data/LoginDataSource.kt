package com.example.firstmultiplatformproject.shared.data

import com.example.firstmultiplatformproject.shared.data.model.LoggedInUser
import com.example.firstmultiplatformproject.shared.randomUUID
import io.ktor.utils.io.errors.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(randomUUID(), username)
            if( fakeUser.displayName.contains("test") ) {
                return Result.Failed("Username can not contain test")
            }
            else
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(RuntimeException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}