package com.harry.example.covid_19tracker.helper

import androidx.recyclerview.widget.DiffUtil
import com.harry.example.covid_19tracker.pojos.State

class StateItemCallBack : DiffUtil.ItemCallback<State>() {
    override fun areItemsTheSame(oldItem: State, newItem: State) = oldItem === newItem

    override fun areContentsTheSame(oldItem: State, newItem: State) = oldItem == newItem

}