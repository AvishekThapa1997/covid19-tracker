package com.harry.example.covid_19tracker.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.harry.example.covid_19tracker.R
import com.harry.example.covid_19tracker.helper.StateAdapter
import com.harry.example.covid_19tracker.pojos.State
import com.harry.example.covid_19tracker.utility.*
import com.harry.example.covid_19tracker.viewmodels.DataViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.koin.core.get
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), KoinComponent {
    private val dataViewModel: DataViewModel by viewModel()
    private lateinit var stateAdapter: StateAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stateAdapter = get()
        if (!stateAdapter.hasObservers())
            stateAdapter.setHasStableIds(true)
        setUpRecyclerView()
        observeStateWiseList()
        observeOverAllData()
        dataViewModel.getData()
    }

    private fun setUpRecyclerView() {
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        recycler_view.adapter = stateAdapter
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )
    }


    private fun observeStateWiseList() {
        dataViewModel.stateWiseResults.observe(this) { response ->
            when (response) {
                is Success -> {
                    stateAdapter.submitList(response.data)
                    if(response.message.isNotEmptyOrIsNotBlank())
                        showMessage(response.message)
                    refresh_layout.visibility = View.VISIBLE
                    data_groups.visibility = View.VISIBLE
                    refresh_layout.apply {
                        setOnRefreshListener {
                            data_groups.visibility = View.GONE
                            startShimmer()
                            dataViewModel.getData()
                            isRefreshing = false
                        }
                    }
                }
                is Error -> {
                    showMessage(response.message)
                    retry.visibility = View.VISIBLE
                    retry.setOnClickListener {
                        startShimmer()
                        dataViewModel.getData()
                        it.visibility = View.GONE
                    }
                }
            }
            stopShimmer()
        }
    }

    private fun stopShimmer() {
        shimmerLayout.stopShimmer()
        shimmerLayout.visibility = View.GONE
    }

    private fun startShimmer() {
        shimmerLayout.startShimmer()
        shimmerLayout.visibility = View.VISIBLE
    }
    private fun showMessage(message : String) = findViewById<View>(android.R.id.content).showMessage(message)
    private suspend fun updateTimeAndData(state: State) {
        val lastUpdatedTime = state.lastUpdatedTime
        val simpleDateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
        val dateAgo = simpleDateFormat.parse(lastUpdatedTime)
        dateAgo?.let { date ->
            last_updated_time.text = getTimeAgo(date)
            runMain {
                confirmed_data.text = state.confirmedCases
                active_data.text = state.activeCases
                recovered_data.text = state.recovered
                deceased_data.text = state.deaths
            }
        }
    }

    private fun getTimeAgo(dateAgo: Date): String {
        val date = Date()
        val hours: Long = TimeUnit.MILLISECONDS.toHours(date.time - dateAgo.time)
        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(date.time - dateAgo.time)
        val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(date.time - dateAgo.time)
        return when {
            seconds < 60 -> "Few Seconds Ago"
            minutes < 60 -> "$minutes minutes ago"
            hours < 24 -> "$hours hour ${minutes % 60} minutes ago"
            else -> try {
                val ago = SimpleDateFormat(
                    "dd/MM/yyyy hh:mm: a",
                    Locale.getDefault()
                ).parse(dateAgo.toString())
                ago?.let {
                    return it.toString()
                }
                return ""
            } catch (exception: Exception) {
                ""
            }
        }
    }

    private fun observeOverAllData() {
        dataViewModel.totalData.observe(this) { totalData ->
            runDefault {
                updateTimeAndData(totalData)
            }
        }
    }
}