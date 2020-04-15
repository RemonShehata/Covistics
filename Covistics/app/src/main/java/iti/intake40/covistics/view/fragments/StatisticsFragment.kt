package iti.intake40.covistics.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import iti.intake40.covistics.R
import iti.intake40.covistics.data.model.SingleCountryStats
import iti.intake40.covistics.view.adapters.CountryStatsAdapter
import iti.intake40.covistics.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_statistics.*

/**
 * A simple [Fragment] subclass.
 */
class StatisticsFragment : Fragment() {

    private val data = MutableLiveData<List<SingleCountryStats>>()
    private val TAG = "StatisticsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_statistics, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val countryStatsList = ArrayList<SingleCountryStats>()

        recycler_view.layoutManager = LinearLayoutManager(this.context)
        val adapter = CountryStatsAdapter(this.context!!)
        recycler_view.adapter = adapter
        viewModel.getAllCountryStats(this)
        viewModel.getSubscribedCountryStat(this)
        viewModel.liveCountryStats.observe(viewLifecycleOwner, Observer {
            // update UI
            //TODo sort here
            adapter?.setCountryData(it)
            Log.d(TAG, it.toString())
            Log.d(TAG, viewModel.liveCountryStats.value.toString())
        })
    }
}
