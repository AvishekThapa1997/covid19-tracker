package com.harry.example.covid_19tracker.pojos

import com.google.gson.annotations.SerializedName

data class State(
    @SerializedName("active") val activeCases: String = "",
    @SerializedName("confirmed") val confirmedCases: String = "",
    @SerializedName("deaths") val deaths: String = "",
    @SerializedName("deltaconfirmed") val deltaConfirmed: String = "",
    val deltaActive: String = "",
    @SerializedName("deltadeaths") val deltaDeaths: String = "",
    @SerializedName("deltarecovered") val deltaRecovered: String = "",
    @SerializedName("lastupdatedtime") val lastUpdatedTime: String = "",
    @SerializedName("recovered") val recovered: String = "",
    @SerializedName("state") val state: String = ""
)