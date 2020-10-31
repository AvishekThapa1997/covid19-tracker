package com.harry.example.covid_19tracker.utility


import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun AppCompatActivity.runDefault(function: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        function()
    }
}

suspend fun runMain(function: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.Main) {
        function()
    }
}


