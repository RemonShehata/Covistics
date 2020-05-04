package iti.intake40.covistics.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import iti.intake40.covistics.core.CovidNotification
import iti.intake40.covistics.core.CovidSharedPreferences
import iti.intake40.covistics.data.database.CountryDAO
import iti.intake40.covistics.data.model.*
import iti.intake40.covistics.data.network.ApiClient
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RepositoryImpl : Repository {

    private val TAG = "RepositoryImpl"
    private lateinit var dao: CountryDAO
    private lateinit var context: Context
    val liveSingleCountriesStatData: MutableLiveData<List<SingleCountryStats>> =
        MutableLiveData<List<SingleCountryStats>>()
    val liveSubscribedCountryData: MutableLiveData<SubscribedCountryData> =
        MutableLiveData<SubscribedCountryData>()
    val liveSharedPreferencesData: MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    val liveWorldwideData: MutableLiveData<WorldWideStat> = MutableLiveData()

    fun init(dao: CountryDAO) {
        this.dao = dao
    }

    fun init(dao: CountryDAO, context: Context) {
        this.dao = dao
        this.context = context
    }

    override fun getDataFromAPI() {
        val call = ApiClient.getClient.getAllCountryStats()

        call.enqueue(object : Callback<CountryStats> {
            override fun onResponse(
                call: Call<CountryStats>,
                response: Response<CountryStats>
            ) {

                if (CovidSharedPreferences.isCountrySubscribed) {
                    getSubscribedCountryDataFromAPI()
                }
                //add the list from api to my list
                liveSingleCountriesStatData.postValue(response.body()?.countriesStat)

                //insert api results in sqllite
                response.body()?.countriesStat?.let { insert(it) }
            }

            override fun onFailure(call: Call<CountryStats>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })

    }

    private fun getSubscribedCountryDataFromAPI() {
        val call = ApiClient.getClient.getSubscribedCountryStat(CovidSharedPreferences.countryName)
        call.enqueue(object : Callback<SubscribedCountryStat> {
            override fun onResponse(
                call: Call<SubscribedCountryStat>,
                response: Response<SubscribedCountryStat>
            ) {
                liveSubscribedCountryData.postValue(response.body()?.countryLastestStat?.get(0))
            }

            override fun onFailure(call: Call<SubscribedCountryStat>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
    }

    override fun getDataFromDatabase(lifecycleOwner: LifecycleOwner) {
        dao.getAllCountries().observe(lifecycleOwner, Observer {
            Log.d(TAG, it.toString())
            liveSingleCountriesStatData.postValue(it)
        })
//        liveData = dao.getAllCountries()
//        val l: List<SingleCountryStats>? = dao.getAllCountries()
//        liveData.postValue(l)
    }

    override fun getCountriesData(lifecycleOwner: LifecycleOwner) {
        if (isConnected()) {
            getDataFromAPI()
        } else {
            getDataFromDatabase(lifecycleOwner)
        }
    }

    override fun getWorldWideData() {
        val call = ApiClient.getClient.getWorldWideStat()
        call.enqueue(object : Callback<WorldWideStat> {

            override fun onResponse(call: Call<WorldWideStat>, response: Response<WorldWideStat>) {
                Log.d("Worldwide", response.body().toString())
                liveWorldwideData.postValue(response.body())
            }

            override fun onFailure(call: Call<WorldWideStat>, t: Throwable) {
                Log.e(TAG, t.toString())
            }

        })
    }

    override fun getSharedPreferencesData() {
        val sharedPreferencesData = ArrayList<String>()
        sharedPreferencesData.add(CovidSharedPreferences.isCountrySubscribed.toString())
        sharedPreferencesData.add(CovidSharedPreferences.countryName.toString())
        sharedPreferencesData.add(CovidSharedPreferences.cases.toString())
        sharedPreferencesData.add(CovidSharedPreferences.deaths.toString())
        sharedPreferencesData.add(CovidSharedPreferences.recovered.toString())
        liveSharedPreferencesData.postValue(sharedPreferencesData)
    }

    override fun setSharedPreferencesData(
        isCountrySubscribed: Boolean,
        countryName: String?,
        cases: String?,
        deaths: String?,
        recovered: String?
    ) {
        CovidSharedPreferences.isCountrySubscribed = isCountrySubscribed
        CovidSharedPreferences.countryName = countryName
        CovidSharedPreferences.cases = cases
        CovidSharedPreferences.deaths = deaths
        CovidSharedPreferences.recovered = recovered
        getSharedPreferencesData()
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