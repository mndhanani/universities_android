package com.android.universities.repo

import com.android.universities.data.University
import com.android.universities.remote.UniversitiesRetrofit
import com.android.universities.remote.response.UniversityResponse
import com.android.universities.room.UniversitiesDao
import com.android.universities.util.NetworkUtil
import com.android.universities.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A repository to fetch universities from a web service and cache the data in the local DB.
 *
 * This repository handles the logic for retrieving university data from a remote web service
 * and caching it locally using Room. It also provides a mechanism to retrieve cached data
 * when the network is unavailable.
 *
 * @property networkUtil A utility class for checking network connectivity.
 * @property retrofit A Retrofit interface for making API calls to fetch university data.
 * @property universitiesDao A DAO for performing database operations on the universities table.
 */
@Singleton
class UniversitiesRepository @Inject constructor(
    private val networkUtil: NetworkUtil,
    private val retrofit: UniversitiesRetrofit,
    private val universitiesDao: UniversitiesDao,
) {

    /**
     * Fetches universities from the web service and caches the data in the local DB.
     *
     * @return A [Flow] emitting the [Result] of the operation, which can be loading, success, or error.
     */
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
     * Handles the failure of fetching universities from the web service.
     *
     * This function attempts to fetch the universities from the local DB if an error occurs
     * during the web service fetch. If cached data is found, it returns a success result with
     * the data. Otherwise, it returns an error result with the provided error message.
     *
     * @param errorMessage The error message to return if no cached data is found.
     * @return A [Result] object containing either the cached data or an error message.
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