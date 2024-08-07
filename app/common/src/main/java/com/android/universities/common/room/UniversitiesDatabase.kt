package com.android.universities.common.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.universities.common.data.University

/**
 * Define the Room database with entities and DAO interface.
 */
@Database(entities = [University::class], version = 1)
abstract class UniversitiesDatabase : RoomDatabase() {
    abstract fun universitiesDao(): UniversitiesDao
}