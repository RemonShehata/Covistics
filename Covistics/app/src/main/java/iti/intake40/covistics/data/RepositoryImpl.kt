package iti.intake40.covistics.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
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
    val liveData: MutableLiveData<List<SingleCountryStats>> =
        MutableLiveData<List<SingleCountryStats>>()

    override fun getDataFromAPI() {
        val call = ApiClient.getClient.getAllCountryStats()

        call.enqueue(object : Callback<CountryStats> {
            override fun onResponse(
                call: Call<CountryStats>,
                response: Response<CountryStats>
            ) {
                //add the list from api to my list
                liveData.postValue(response.body()?.countriesStat)
                //insert api results in sqllite
                response.body()?.countriesStat?.let { insert(it) }
            }

            override fun onFailure(call: Call<CountryStats>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })

    }

    override fun getDataFromDatabase() {
        val l: List<SingleCountryStats>? = dao.getAllCountries().value
        liveData.postValue(l)
    }


    override fun getCountriesData() {
        if (isConnected()) {
            getDataFromAPI()
        } else {
            getDataFromDatabase()
        }
    }


    fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val status: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return status
    }

    fun insert(countries: List<SingleCountryStats>) = runBlocking {
        dao.insertAll(countries)
    }
}