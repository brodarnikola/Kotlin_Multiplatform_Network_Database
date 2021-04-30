package com.example.firstmultiplatformproject.android.presentation.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null,
    val failed: String? = null
)