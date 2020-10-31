package com.harry.example.covid_19tracker.repository


import com.harry.example.covid_19tracker.api.ApiClient
import com.harry.example.covid_19tracker.database.AppDatabase
import com.harry.example.covid_19tracker.pojos.ApiResponse

class AppRepositoryImpl(private val apiClient: ApiClient, private val appDatabase: AppDatabase) :
    AppRepository {

    @Throws(Exception::class)
    override suspend fun getData(): ApiResponse? {
        val response = apiClient.getData()
        if (response.isSuccessful)
            return response.body()
        return null
    }

    @Throws(Exception::class)
    override suspend fun getLocalData(): ApiResponse? {
        return appDatabase.dataDao().getLocalData()
    }

    @Throws(Exception::class)
    override suspend fun insertData(apiResponse: ApiResponse) {
        appDatabase.dataDao().insertData(apiResponse)
    }
}