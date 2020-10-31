package com.harry.example.covid_19tracker.repository

import com.harry.example.covid_19tracker.pojos.ApiResponse
import com.harry.example.covid_19tracker.pojos.State

interface AppRepository {
   suspend fun getData() : ApiResponse?
    suspend fun getLocalData() : ApiResponse?
    suspend fun insertData(apiResponse: ApiResponse)
}