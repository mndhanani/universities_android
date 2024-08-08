package com.android.universities.repo

import com.android.universities.data.University
import com.android.universities.remote.UniversitiesRetrofit
import com.android.universities.room.UniversitiesDao
import com.android.universities.util.NetworkUtil
import com.android.universities.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A repository to fetch universities from web service and cache the data in the local DB.
 */
@Singleton
class UniversitiesRepository @Inject constructor(
    private val networkUtil: NetworkUtil,
    private val retrofit: UniversitiesRetrofit,
    private val universitiesDao: UniversitiesDao,
) {

    suspend fun getUniversities(): Flow<Result<List<University>>> {
        return flow {
            // Emit Loading status.
            emit(Result.loading())

            if (networkUtil.isConnectedToInternet()) {
                try {
                    retrofit.searchUniversities(country = "United Arab Emirates")
                        .let { responseList ->
                            /**
                             * Map [UniversityResponse] list to [University] list.
                             */
                            val universities = responseList.map {
                                University(
                                    name = it.name,
                                    state = it.state,
                                    country = it.country,
                                    countryCode = it.countryCode,
                                    webPage = it.webPages?.firstOrNull()
                                )
                            }

                            // Clear the current cache of universities and replace it with a new list.
                            universitiesDao.refreshCache(universities)

                            // Emit Success status with data.
                            emit(Result.success(data = universities))
                        }
                } catch (e: Exception) {
                    val resultOnFailure =
                        onGetUniversitiesFailure(errorMessage = e.message ?: "Something went wrong")
                    emit(resultOnFailure)
                }
            } else {
                val resultOnFailure = onGetUniversitiesFailure(errorMessage = "No internet")
                emit(resultOnFailure)
            }
        }
    }

    /**
     * On getUniversities failure, fetch the universities from local DB if exists
     * and return Result with Success status and data,
     * otherwise return Result with Error status and error message.
     */
    private suspend fun onGetUniversitiesFailure(errorMessage: String): Result<List<University>> {
        universitiesDao.getFromCache().let { universities ->
            return if (universities.isNotEmpty()) {
                Result.success(data = universities)
            } else {
                Result.error(message = errorMessage)
            }
        }
    }
}