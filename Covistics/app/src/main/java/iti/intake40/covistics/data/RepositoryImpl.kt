package iti.intake40.covistics.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import iti.intake40.covistics.data.model.CountryStats
import iti.intake40.covistics.data.model.SingleCountryStats
import iti.intake40.covistics.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class RepositoryImpl : Repository {
    private val TAG = "RepositoryImpl"

    override fun getSavedCountryStats(): LiveData<List<SingleCountryStats>> {
        val liveCountryStats = MutableLiveData<List<SingleCountryStats>>()
        val call = ApiClient.getClient.getAllCountryStats()

        call.enqueue(object : Callback<CountryStats> {
            override fun onResponse(
                call: Call<CountryStats>,
                response: Response<CountryStats>
            ) {
                //response.body()?.let { countryStatsList.addAll(it) }
                //countryStatsList.addAll(response!!.body()!!)
                liveCountryStats.postValue(response.body()?.countriesStat)
                response.body()?.countriesStat

                print("------")
                Log.d(TAG, response.body()?.countriesStat?.size.toString())
                Log.d(TAG, response.body()?.countriesStat.toString())
                //Log.d(TAG, response.toString())
            }

            override fun onFailure(call: Call<CountryStats>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
        Log.d(TAG, liveCountryStats.toString())
        return liveCountryStats
    }
}