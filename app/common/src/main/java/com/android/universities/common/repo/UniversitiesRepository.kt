package com.android.universities.common.repo

import com.android.universities.common.data.University
import com.android.universities.common.remote.UniversitiesRetrofit
import com.android.universities.common.remote.response.UniversityResponse
import com.android.universities.common.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A repository to fetch universities from web service and cache the data in the local DB.
 */
@Singleton
class UniversitiesRepository @Inject constructor(
    private val retrofit: UniversitiesRetrofit,
) {

    suspend fun getUniversities(): Flow<Result<List<University>>> {
        return flow {
            // Emit Loading status.
            emit(Result.loading())

            try {
                retrofit.searchUniversities(country = "United Arab Emirates").let { responseList ->
                    /**
                     * Map [UniversityResponse] list to [University] list.
                     */
                    val universities = responseList.map {
                        University(
                            it.name,
                            it.state,
                            it.country,
                            it.countryCode,
                            it.webPages?.firstOrNull()
                        )
                    }

                    // Emit Success status with data.
                    emit(Result.success(data = universities))
                }
            } catch (e: Exception) {
                // Emit Error status with message.
                emit(Result.error(message = "Something went wrong."))
            }
        }
    }
}