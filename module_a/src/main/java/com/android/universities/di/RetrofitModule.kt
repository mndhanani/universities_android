package com.android.universities.di

import com.android.universities.remote.UniversitiesRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dependency Injection
 * Create the Hilt module for providing the Retrofit instance.
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    const val API_BASE_URL = "http://universities.hipolabs.com/"

    @Singleton
    @Provides
    fun provideRetrofit(retrofit: Retrofit.Builder): UniversitiesRetrofit =
        retrofit.build().create(UniversitiesRetrofit::class.java)

    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder().build()
}