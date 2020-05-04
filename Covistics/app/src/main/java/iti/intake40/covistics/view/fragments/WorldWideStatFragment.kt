package iti.intake40.covistics.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import iti.intake40.covistics.R
import iti.intake40.covistics.data.model.WorldWideStat
import iti.intake40.covistics.data.network.ApiClient
import iti.intake40.covistics.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_world_wide_stat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class WorldWideStatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.liveWorldWideStat.observe(viewLifecycleOwner, Observer {
            //Log.d("WorldwideFragment",it.cases)
            ww_total_cases_tv.text = it.cases
            ww_new_cases_tv.text = it.newCases
            ww_active_cases_tv.text = it.activeCases
            ww_deaths_tv.text = it.deaths
            ww_new_deaths_tv.text = it.newDeaths
            ww_total_recoverd_tv.text = it.totalRecovered
        })
        viewModel.getWorldwideData(this)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_world_wide_stat, container, false)
    }

}
