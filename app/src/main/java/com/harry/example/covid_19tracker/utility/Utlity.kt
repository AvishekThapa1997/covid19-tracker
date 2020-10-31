package com.harry.example.covid_19tracker.utility


import android.view.View
import com.google.android.material.snackbar.Snackbar

fun String.isNotEmptyOrIsNotBlank() = this.isNotEmpty() && this.isNotBlank()

fun View.showMessage(message: String) {
    Snackbar.make(
        this, message,
        Snackbar.LENGTH_LONG
    ).apply {
        setAction("Dismiss"){
            dismiss()
        }
    }.show()
}
