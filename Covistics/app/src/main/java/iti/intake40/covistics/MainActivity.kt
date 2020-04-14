package iti.intake40.covistics

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import iti.intake40.covistics.data.model.SingleCountryStats
import iti.intake40.covistics.view.adapters.CountryStatsAdapter
import iti.intake40.covistics.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"//::class.java.simpleName
    private val data = MutableLiveData<List<SingleCountryStats>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val countryStatsList = ArrayList<SingleCountryStats>()
        recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = CountryStatsAdapter()
        recycler_view.adapter = adapter
        viewModel.getAllCountryStats(this)
        viewModel.getSubscribedCountryStat(this)
        viewModel.liveCountryStats.observe(this, Observer {
            // update UI
            //TODo sort here
            adapter.setCountryData(it)
            Log.d(TAG, it.toString())
            Log.d(TAG, viewModel.liveCountryStats.value.toString())
        })


    }

}

