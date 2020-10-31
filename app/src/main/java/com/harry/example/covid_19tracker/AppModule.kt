package com.harry.example.covid_19tracker

import androidx.room.Room
import com.harry.example.covid_19tracker.api.ApiClient
import com.harry.example.covid_19tracker.database.AppDatabase
import com.harry.example.covid_19tracker.helper.StateAdapter
import com.harry.example.covid_19tracker.repository.AppRepositoryImpl
import com.harry.example.covid_19tracker.utility.BASE_URL
import com.harry.example.covid_19tracker.viewmodels.DataViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModules = module {
    single {
        OkHttpClient.Builder().apply {
            connectTimeout(5, TimeUnit.SECONDS)
            readTimeout(5, TimeUnit.SECONDS)
        }.build()
    }
    single {
        val okHttpClient: OkHttpClient = get()
        Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(ApiClient::class.java)
    }
    factory {
        StateAdapter()
    }
    single { AppRepositoryImpl(get(), get()) }
    viewModel { DataViewModel(get<AppRepositoryImpl>()) }
}
val databaseModules = module {
    single {
        Room.databaseBuilder(
            androidApplication().applicationContext,
            AppDatabase::class.java,
            "data.db"
        ).build()
    }
}