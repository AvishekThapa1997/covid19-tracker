package com.harry.example.covid_19tracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harry.example.covid_19tracker.database.converters.DataTypeConverters
import com.harry.example.covid_19tracker.pojos.ApiResponse

@Database(entities = [ApiResponse::class], version = 1)
@TypeConverters(DataTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}