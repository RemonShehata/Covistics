package iti.intake40.covistics.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import iti.intake40.covistics.core.CovidNotification
import iti.intake40.covistics.core.CovidSharedPreferences
import iti.intake40.covistics.data.RepositoryImpl
import iti.intake40.covistics.data.database.CountryDAO
import iti.intake40.covistics.data.database.CountryRoomDatabase
import iti.intake40.covistics.data.model.SingleCountryStats
import iti.intake40.covistics.data.model.SubscribedCountryData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: CountryDAO
    val liveCountryStats: MutableLiveData<List<SingleCountryStats>>
    init {
        dao = CountryRoomDatabase.getDatabase(application).countryDao()
        RepositoryImpl.init(dao, getApplication())
        liveCountryStats = MutableLiveData<List<SingleCountryStats>>()
    }

    fun getSubscribedCountryStat(lifecycleOwner: LifecycleOwner){
        RepositoryImpl.liveSubscribedCountryData.observe(lifecycleOwner, Observer {
            onSubsrcibedCountryUpdate(it)
        })
       // RepositoryImpl.getSubscribedCountryDataFromAPI()
    }

    fun getAllCountryStats(lifecycleOwner: LifecycleOwner) {
        RepositoryImpl.liveSingleCountriesStatData.observe(lifecycleOwner, Observer {
            liveCountryStats.postValue(it)
        })
        RepositoryImpl.getCountriesData(lifecycleOwner)
    }

    fun onSubsrcibedCountryUpdate(newSubscribedCountryData: SubscribedCountryData){
        val oldSubscribedCountryData = SubscribedCountryData(CovidSharedPreferences.countryName, CovidSharedPreferences.cases, "", "", CovidSharedPreferences.deaths, "",CovidSharedPreferences.recovered, "", null, "")

        if(oldSubscribedCountryData.cases.equals(newSubscribedCountryData.cases) && oldSubscribedCountryData.deaths.equals(newSubscribedCountryData.deaths) && oldSubscribedCountryData.totalRecovered.equals(newSubscribedCountryData.totalRecovered)){
            Log.d("Eqality","EQUAL")
        }else{
            Log.d("Eqality","NOT EQUAL")
            Log.d("Shared",CovidSharedPreferences.isCountrySubscribed.toString())
            Log.d("Shared",CovidSharedPreferences.countryName.toString())
            Log.d("Shared",CovidSharedPreferences.cases.toString())
            Log.d("Shared",CovidSharedPreferences.deaths.toString())
            Log.d("Shared",CovidSharedPreferences.recovered.toString())
            updateSubscribedCounrtyPreferences(newSubscribedCountryData)
            CovidNotification.pushNotification(newSubscribedCountryData)
        }
    }

    private fun updateSubscribedCounrtyPreferences(newSubscribedCountryData: SubscribedCountryData){
        CovidSharedPreferences.countryName = newSubscribedCountryData.countryName
        CovidSharedPreferences.cases = newSubscribedCountryData.cases
        CovidSharedPreferences.deaths = newSubscribedCountryData.deaths
        CovidSharedPreferences.recovered = newSubscribedCountryData.totalRecovered
    }

}