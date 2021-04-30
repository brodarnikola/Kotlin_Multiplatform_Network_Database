package com.example.firstmultiplatformproject.android.presentation.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null,
    val failed: String? = null
)