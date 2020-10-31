package com.harry.example.covid_19tracker.helper

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.harry.example.covid_19tracker.pojos.State
import com.harry.example.covid_19tracker.utility.ARROW
import com.harry.example.covid_19tracker.utility.isNotEmptyOrIsNotBlank
import kotlinx.android.synthetic.main.state_layout.view.*
import kotlinx.android.synthetic.main.state_layout.view.state_confirmed_data
import kotlinx.android.synthetic.main.state_layout.view.state_recovered_data

class StateViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun onBind(currentState: State) {
        view.apply {
            state_name.text = currentState.state
            state_confirmed_data.text = currentState.confirmedCases
            delta_state_confirmed_data.text = data(currentState.deltaConfirmed)
            state_active_data.text = currentState.activeCases
            delta_state_active_data.text = data(currentState.deltaActive)
            state_recovered_data.text = currentState.recovered
            delta_state_recovered_data.text = data(currentState.deltaRecovered)
            deceased_data.text = currentState.deaths
            delta_state_deceased_data.text = data(currentState.deltaDeaths)
        }
    }

    private fun data(data: String) : String {
        return if (data.isNotEmptyOrIsNotBlank())
            ARROW.plus(data)
        else
            ARROW.plus("0")
    }

}