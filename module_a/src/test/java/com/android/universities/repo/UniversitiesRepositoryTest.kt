package com.android.universities.repo

import com.android.universities.data.University
import com.android.universities.remote.UniversitiesRetrofit
import com.android.universities.remote.response.UniversityResponse
import com.android.universities.room.UniversitiesDao
import com.android.universities.util.NetworkUtil
import com.android.universities.util.Result
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.exceptions.base.MockitoException
import kotlin.test.Test
import kotlin.test.assertEquals

class UniversitiesRepositoryTest {
    private val networkUtil: NetworkUtil = mock()
    private val retrofit: UniversitiesRetrofit = mock()
    private val universitiesDao: UniversitiesDao = mock()
    private val repository = UniversitiesRepository(networkUtil, retrofit, universitiesDao)

    private val responseList = listOf(
        UniversityResponse(
            "Mohamed bin Zayed University of Artificial Intelligence (MBZUAI)",
            "Abu Dhabi",
            "United Arab Emirates",
            "AE",
            listOf("https://mbzuai.ac.ae/")
        ),
        UniversityResponse(
            "American College Of Dubai",
            null,
            "United Arab Emirates",
            "AE",
            listOf("http://www.acd.ac.ae/")
        ),
        UniversityResponse(
            "Abu Dhabi University",
            null,
            "United Arab Emirates",
            "AE",
            listOf("http://www.adu.ac.ae/")
        )
    )

    private val universities = responseList.map {
        University(
            name = it.name,
            state = it.state,
            country = it.country,
            countryCode = it.countryCode,
            webPage = it.webPages?.firstOrNull()
        )
    }

    @Test
    fun `test getUniversities success`() = runTest {
        // Mock result success with responseList data.
        `when`(networkUtil.isConnectedToInternet())
            .thenReturn(true)
        `when`(retrofit.searchUniversities("United Arab Emirates"))
            .thenReturn(responseList)

        testSuccessResult(repository.getUniversities().toList())
    }

    @Test
    fun `test getUniversities error with cached data`() = runTest {
        // Mock result error.
        `when`(networkUtil.isConnectedToInternet())
            .thenReturn(true)
        `when`(retrofit.searchUniversities("United Arab Emirates"))
            .thenThrow(MockitoException("Unable to retrieve data."))
        `when`(universitiesDao.getFromCache())
            .thenReturn(universities)

        testSuccessResult(repository.getUniversities().toList())
    }

    @Test
    fun `test getUniversities error with no cached data`() = runTest {
        // Mock result error.
        `when`(networkUtil.isConnectedToInternet())
            .thenReturn(true)
        `when`(retrofit.searchUniversities("United Arab Emirates"))
            .thenThrow(MockitoException("Unable to retrieve data."))
        `when`(universitiesDao.getFromCache())
            .thenReturn(emptyList())

        val result = repository.getUniversities().toList()
        assertEquals(Result.Status.LOADING, result[0].status)
        assertEquals(Result.Status.ERROR, result[1].status)
        assertEquals("Unable to retrieve data.", result[1].message)
    }

    @Test
    fun `test getUniversities no internet with cached data`() = runTest {
        // Mock result error for no internet.
        `when`(networkUtil.isConnectedToInternet())
            .thenReturn(false)
        `when`(universitiesDao.getFromCache())
            .thenReturn(universities)

        testSuccessResult(repository.getUniversities().toList())
    }

    @Test
    fun `test getUniversities no internet with no cached data`() = runTest {
        // Mock result error for no internet.
        `when`(networkUtil.isConnectedToInternet())
            .thenReturn(false)
        `when`(universitiesDao.getFromCache())
            .thenReturn(emptyList())

        val result = repository.getUniversities().toList()
        assertEquals(Result.Status.LOADING, result[0].status)
        assertEquals(Result.Status.ERROR, result[1].status)
        assertEquals("No internet", result[1].message)
    }

    private fun testSuccessResult(result: List<com.android.universities.util.Result<List<University>>>) {
        assertEquals(Result.Status.LOADING, result[0].status)
        assertEquals(Result.Status.SUCCESS, result[1].status)

        val universities: List<University>? = result[1].data
        assertEquals(3, universities?.size)
        assertEquals(
            "Mohamed bin Zayed University of Artificial Intelligence (MBZUAI)",
            universities?.get(0)?.name
        )
        assertEquals("American College Of Dubai", universities?.get(1)?.name)
        assertEquals("Abu Dhabi University", universities?.get(2)?.name)
    }
}