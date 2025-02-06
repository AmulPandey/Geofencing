package com.example.location_tracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.location_tracker.data.local.entity.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Query("SELECT * FROM locations ORDER BY createdAt DESC")
    fun getAllLocations(): Flow<List<Location>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Query("SELECT * FROM locations WHERE id = :id")
    suspend fun getLocationById(id: Long): Location?

    @Delete
    suspend fun deleteLocation(location: Location)
}