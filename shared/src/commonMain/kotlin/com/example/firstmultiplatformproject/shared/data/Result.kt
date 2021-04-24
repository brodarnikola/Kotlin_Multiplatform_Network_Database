package com.example.firstmultiplatformproject.shared.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Failed<out T : String>(val data: String) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failed<*> -> "Failed[data0$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}