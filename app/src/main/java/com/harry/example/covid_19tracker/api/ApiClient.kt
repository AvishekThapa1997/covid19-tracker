package com.harry.example.covid_19tracker.api

import com.harry.example.covid_19tracker.pojos.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {
    @GET("/data.json")
    suspend fun getData() :Response<ApiResponse>
}