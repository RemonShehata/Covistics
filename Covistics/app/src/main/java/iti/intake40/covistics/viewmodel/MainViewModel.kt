package iti.intake40.covistics.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import iti.intake40.covistics.core.CovidNotification
import iti.intake40.covistics.data.RepositoryImpl
import iti.intake40.covistics.data.database.CountryDAO
import iti.intake40.covistics.data.database.CountryRoomDatabase
import iti.intake40.covistics.data.model.SingleCountryStats
import iti.intake40.covistics.data.model.SubscribedCountryData
import iti.intake40.covistics.data.model.WorldWideStat

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: CountryDAO
    val liveCountryStats: MutableLiveData<List<SingleCountryStats>>
    val liveSharedPreferencesData : MutableLiveData<List<String>>
    val liveWorldWideStat : MutableLiveData<WorldWideStat>

    init {
        dao = CountryRoomDatabase.getDatabase(application).countryDao()
        RepositoryImpl.init(dao, getApplication())
        liveCountryStats = MutableLiveData<List<SingleCountryStats>>()
        liveSharedPreferencesData = MutableLiveData<List<String>>()
        liveWorldWideStat = MutableLiveData()
    }

    fun getAllCountryStats(lifecycleOwner: LifecycleOwner) {
        RepositoryImpl.liveSingleCountriesStatData.observe(lifecycleOwner, Observer {
            liveCountryStats.postValue(it
                .filter { it.countryName.isNotEmpty() }
                .sortedByDescending { it.cases.replace(",", "").toInt() })
        })
        RepositoryImpl.getCountriesData(lifecycleOwner)
    }

    fun getSharedPreferencesData(lifecycleOwner: LifecycleOwner) {
        RepositoryImpl.liveSharedPreferencesData.observe(lifecycleOwner, Observer {
            liveSharedPreferencesData.postValue(it)
        })
        RepositoryImpl.getSharedPreferencesData()
    }

    fun setSharedPreferencesData(isCountrySubscribed: Boolean, countryName: String?) {
        RepositoryImpl.setSharedPreferencesData(isCountrySubscribed, countryName, null, null, null)
    }

    fun getWorldwideData(lifecycleOwner: LifecycleOwner){
        RepositoryImpl.liveWorldwideData.observe(lifecycleOwner, Observer {
            liveWorldWideStat.postValue(it)
        })
        RepositoryImpl.getWorldWideData()
    }
}