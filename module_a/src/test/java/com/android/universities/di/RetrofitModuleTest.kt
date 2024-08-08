package com.android.universities.di

import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito.mock

class RetrofitModuleTest {

    @Test
    fun `test API_BASE_URL`() {
        assertEquals("http://universities.hipolabs.com/", RetrofitModule.API_BASE_URL)
    }

    @Test
    fun `test provideRetrofitBuilder`() {
        val okHttpClient: OkHttpClient = mock()
        val retrofitBuilder = RetrofitModule.provideRetrofitBuilder(okHttpClient)

        assertEquals(RetrofitModule.API_BASE_URL, retrofitBuilder.build().baseUrl().toString())
        assertEquals(okHttpClient, retrofitBuilder.build().callFactory())
        assert(retrofitBuilder.converterFactories().isNotEmpty())
    }

    @Test
    fun `test provideRetrofit`() {
        val okHttpClient: OkHttpClient = mock()
        val retrofitBuilder = RetrofitModule.provideRetrofitBuilder(okHttpClient)
        val universitiesRetrofit = RetrofitModule.provideRetrofit(retrofitBuilder)

        assertNotNull(universitiesRetrofit)
    }

    @Test
    fun `test provideOkHttpClient`() {
        val okHttpClient: OkHttpClient = RetrofitModule.provideOkHttpClient()

        assertNotNull(okHttpClient)
    }
}