package com.android.universities.common.repo

import com.android.universities.common.data.University
import com.android.universities.common.remote.UniversitiesRetrofit
import com.android.universities.common.remote.response.UniversityResponse
import com.android.universities.common.util.NetworkUtil
import com.android.universities.common.util.Result
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.exceptions.base.MockitoException
import kotlin.test.Test
import kotlin.test.assertEquals

class UniversitiesRepositoryTest {
    private val retrofit: UniversitiesRetrofit = mock()
    private val networkUtil: NetworkUtil = mock()
    private val repository = UniversitiesRepository(retrofit, networkUtil)

    @Test
    fun `test getUniversities success`() = runTest {
        // Mock result success with the following data.
        val responseList = listOf(
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
        `when`(networkUtil.isConnectedToInternet())
            .thenReturn(true)
        `when`(retrofit.searchUniversities("United Arab Emirates"))
            .thenReturn(responseList)
        val result = repository.getUniversities().toList()

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

    @Test
    fun `test getUniversities error`() = runTest {
        // Mock result error.
        `when`(networkUtil.isConnectedToInternet())
            .thenReturn(true)
        `when`(retrofit.searchUniversities("United Arab Emirates"))
            .thenThrow(MockitoException("Unable to retrieve data."))
        val result = repository.getUniversities().toList()

        assertEquals(Result.Status.LOADING, result[0].status)
        assertEquals(Result.Status.ERROR, result[1].status)
        assertEquals("Unable to retrieve data.", result[1].message)
    }

    @Test
    fun `test getUniversities no internet`() = runTest {
        // Mock result error for no internet.
        `when`(networkUtil.isConnectedToInternet())
            .thenReturn(false)
        val result = repository.getUniversities().toList()

        assertEquals(Result.Status.LOADING, result[0].status)
        assertEquals(Result.Status.ERROR, result[1].status)
        assertEquals("No internet", result[1].message)
    }
}