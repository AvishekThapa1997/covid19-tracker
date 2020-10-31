package com.harry.example.covid_19tracker.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.harry.example.covid_19tracker.pojos.State

object DataTypeConverters {
    @JvmStatic
    @TypeConverter
    fun fromList(data: List<State>) = Gson().toJson(data)

    @JvmStatic
    @TypeConverter
    fun toList(data: String): List<State> {
        val listType = object : TypeToken<List<State>>() {}.type
        return Gson().fromJson(data, listType)
    }
}