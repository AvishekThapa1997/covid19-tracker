package com.harry.example.covid_19tracker.utility

sealed class Resource<T>
class Success<T>(val data: T,val message : String = "" ) : Resource<T>()
class Error<T>(val message: String) : Resource<T>()