package iti.intake40.covistics.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import iti.intake40.covistics.data.RepositoryImpl
import iti.intake40.covistics.data.database.CountryDAO
import iti.intake40.covistics.data.database.CountryRoomDatabase
import iti.intake40.covistics.data.model.SingleCountryStats

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: CountryDAO
    val liveCountryStats: MutableLiveData<List<SingleCountryStats>>

    init {
        dao = CountryRoomDatabase.getDatabase(application).countryDao()
        RepositoryImpl.init(dao,getApplication())
        liveCountryStats = MutableLiveData<List<SingleCountryStats>>()
    }

    fun getAllCountryStats(lifecycleOwner: LifecycleOwner) {
        RepositoryImpl.liveData.observe(lifecycleOwner, Observer {
            liveCountryStats.postValue(it)
        })
        RepositoryImpl.getCountriesData(lifecycleOwner)
    }
}