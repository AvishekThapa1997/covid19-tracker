package com.harry.example.covid_19tracker.helper


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.harry.example.covid_19tracker.R
import com.harry.example.covid_19tracker.pojos.State

class StateAdapter : RecyclerView.Adapter<StateViewHolder>() {
    private val asyncListDiffer: AsyncListDiffer<State> = AsyncListDiffer(this, StateItemCallBack())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StateViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.state_layout, parent, false)
    )

    override fun getItemCount() = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        val currentState = asyncListDiffer.currentList[position]
        holder.onBind(currentState)

    }

    override fun getItemId(position: Int) = position.toLong()
    fun submitList(stateList: List<State>) = asyncListDiffer.submitList(stateList)
}
