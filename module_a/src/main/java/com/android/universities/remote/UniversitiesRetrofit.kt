package com.android.universities.remote

import com.android.universities.remote.response.UniversityResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The interface defines Universities Web Services.
 */

interface UniversitiesRetrofit {
    @GET("search")
    suspend fun searchUniversities(@Query("country") country: String): List<UniversityResponse>
}