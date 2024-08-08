package com.android.universities.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.android.universities.data.University

/**
 * Create the DAO (Data Access Object) interface for accessing the database.
 */
@Dao
interface UniversitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(universities: List<University>)

    @Query("DELETE FROM universities")
    suspend fun deleteAll()

    /**
     * Clears the current cache of universities and replaces it with a new list.
     */
    @Transaction
    suspend fun refreshCache(universities: List<University>) {
        deleteAll()
        insertAll(universities)
    }

    @Query("SELECT * FROM universities")
    suspend fun getFromCache(): List<University>
}