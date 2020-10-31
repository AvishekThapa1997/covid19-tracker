package com.harry.example.covid_19tracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harry.example.covid_19tracker.pojos.ApiResponse
import com.harry.example.covid_19tracker.pojos.State

@Dao
interface DataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(apiResponse: ApiResponse)

    @Query("SELECT * FROM local_data")
    suspend fun getLocalData(): ApiResponse?
}