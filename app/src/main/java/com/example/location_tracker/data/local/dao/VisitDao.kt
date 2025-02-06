package com.example.location_tracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.location_tracker.data.local.entity.Visit
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitDao {

    @Query("SELECT * FROM visits ORDER BY entryTime DESC")
    fun getAllVisits(): Flow<List<Visit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisit(visit: Visit)

    @Query("SELECT * FROM visits WHERE locationId = :locationId")
    fun getVisitsForLocation(locationId: Long): Flow<List<Visit>>

    @Query("SELECT * FROM visits WHERE exitTime IS NULL")
    suspend fun getActiveVisits(): List<Visit>

    @Update
    suspend fun updateVisit(visit: Visit)

    @Delete
    suspend fun deleteVisit(visit: Visit)
}
