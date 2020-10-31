package com.harry.example.covid_19tracker.viewmodels


import androidx.lifecycle.*
import com.harry.example.covid_19tracker.pojos.State
import com.harry.example.covid_19tracker.repository.AppRepository
import com.harry.example.covid_19tracker.utility.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class DataViewModel(private val appRepository: AppRepository) :
    ViewModel() {
    private val stateList: MutableLiveData<Resource<List<State>>> = MutableLiveData()
    private val allOverData: MutableLiveData<State> = MutableLiveData()
    val totalData: LiveData<State>
        get() = allOverData
    val stateWiseResults: LiveData<Resource<List<State>>>
        get() = stateList
    private var extraObject: State? = null
    fun getData() {
        runIO {
            try {
                val response = appRepository.getData()
                response?.let { apiResponse ->
                    filteredList(apiResponse.response)
                    runIO {
                        appRepository.insertData(apiResponse)
                    }
                }
            } catch (exception: Exception) {
                val databaseResponse = appRepository.getLocalData()
                if (databaseResponse != null && databaseResponse.response.isNotEmpty())
                    filteredList(databaseResponse.response, COULD_NOT_REFRESH)
                else
                    stateList.postValue(Error(SOMETHING_WENT_WRONG))
            }
        }
    }

    private fun filteredList(data: List<State>, message: String = "") {
        val newList: MutableList<State> =
            data.toMutableList().subList(1, data.size)
        newList.forEach { currentState ->
            if (currentState.state == STATE_UNASSIGNED) {
                extraObject = currentState
                return@forEach
            }
        }
        extraObject?.let {
            newList.remove(extraObject)
        }
        newList.sortBy { currentState ->
            currentState.state
        }
        allOverData.postValue(data[0])
        if (message.isNotEmptyOrIsNotBlank())
            stateList.postValue(Success(newList, message))
        else
            stateList.postValue(Success(newList))
    }

//    private fun errorMessage(exception: Exception): String {
//        return when (exception) {
//            is UnknownHostException -> UNKNOWN_HOST_EXCEPTION
//            is SocketTimeoutException -> TIMEOUT_EXCEPTION
//            is ConnectException -> CONNECTION_EXCEPTION
//            else -> SSLHANDSHAKE_EXCEPTION
//        }
//    }
}