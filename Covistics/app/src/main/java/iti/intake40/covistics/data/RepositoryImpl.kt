package iti.intake40.covistics.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import iti.intake40.covistics.data.database.CountryDAO
import iti.intake40.covistics.data.model.CountryStats
import iti.intake40.covistics.data.model.SingleCountryStats
import iti.intake40.covistics.data.network.ApiClient
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl(val dao: CountryDAO, val context: Context) : Repository {

    private val TAG = "RepositoryImpl"

    override fun getDataFromAPI(): LiveData<List<SingleCountryStats>> {
        val liveCountryStats = MutableLiveData<List<SingleCountryStats>>()
        val call = ApiClient.getClient.getAllCountryStats()

        call.enqueue(object : Callback<CountryStats> {
            override fun onResponse(
                call: Call<CountryStats>,
                response: Response<CountryStats>
            ) {

                liveCountryStats.postValue(response.body()?.countriesStat)
                //add to database
                response.body()?.countriesStat?.let { insert(it) }
            }

            override fun onFailure(call: Call<CountryStats>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
        Log.d(TAG, liveCountryStats.toString())
        return liveCountryStats
    }

    override fun getDataFromDatabase() = dao.getAllCountries()

    override fun getCountriesData(): LiveData<List<SingleCountryStats>> {
        if (isConnected()) {
            return getDataFromAPI()
        } else {
            return getDataFromDatabase()
        }
    }

    fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        return isConnected
    }

    fun insert(countries: List<SingleCountryStats>) = runBlocking {
        dao.insertAll(countries)
    }
}