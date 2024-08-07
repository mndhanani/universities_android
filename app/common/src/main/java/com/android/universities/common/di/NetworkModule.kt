package com.android.universities.common.di

import android.content.Context
import android.net.ConnectivityManager
import com.android.universities.common.util.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency Injection
 * Create the Hilt module for providing the ConnectivityManager instance.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkUtil(connectivityManager: ConnectivityManager) =
        NetworkUtil(connectivityManager)

    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}