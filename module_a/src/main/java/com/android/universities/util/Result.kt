package com.android.universities.util

/**
 * Generic class for holding success response, message and loading status.
 */
data class Result<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?, message: String? = null): Result<T> =
            Result(Status.SUCCESS, data, message)

        fun <T> error(data: T? = null, message: String?): Result<T> =
            Result(Status.ERROR, data, message)

        fun <T> loading(data: T? = null, message: String? = null): Result<T> =
            Result(Status.LOADING, data, message)
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, message=$message)"
    }
}