package com.android.universities.common.di

import android.content.Context
import androidx.room.Room
import com.android.universities.common.room.UniversitiesDao
import com.android.universities.common.room.UniversitiesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency Injection
 * Create the Hilt module for providing the database and DAO instance.
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UniversitiesDatabase {
        return Room.databaseBuilder(
            context, UniversitiesDatabase::class.java, "universities_database"
        ).build()
    }

    @Provides
    fun provideDao(database: UniversitiesDatabase): UniversitiesDao = database.universitiesDao()
}