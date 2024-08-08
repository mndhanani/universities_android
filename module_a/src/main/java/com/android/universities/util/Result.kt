package com.android.universities.util

/**
 * A generic class that holds a value with its loading status.
 *
 * @param T The type of the data being held by this result.
 * @property status The current status of the result.
 * @property data The data held by the result, if available.
 * @property message An optional message providing additional details about the result.
 */
data class Result<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
) {

    /**
     * Enum representing the status of a result.
     */
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