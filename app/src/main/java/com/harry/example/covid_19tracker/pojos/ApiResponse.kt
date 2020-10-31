package com.harry.example.covid_19tracker.pojos

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "local_data", indices = [Index("state_data", unique = true)])
data class ApiResponse(
    @ColumnInfo(name = "state_data")
    @SerializedName("statewise") val response: List<State>,
    @PrimaryKey(autoGenerate = true)
    val primaryId: Int = 0
)
