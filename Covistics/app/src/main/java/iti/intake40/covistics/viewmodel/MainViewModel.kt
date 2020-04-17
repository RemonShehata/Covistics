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
    val liveSharedPreferencesData : MutableLiveData<List<String>>
    var oldSubscribedCountryData : SubscribedCountryData? = null

    init {
        dao = CountryRoomDatabase.getDatabase(application).countryDao()
        RepositoryImpl.init(dao, getApplication())
        liveCountryStats = MutableLiveData<List<SingleCountryStats>>()
        liveSharedPreferencesData = MutableLiveData<List<String>>()
    }

    fun getSubscribedCountryStat(lifecycleOwner: LifecycleOwner){
        RepositoryImpl.liveSubscribedCountryData.observe(lifecycleOwner, Observer {
            onSubsrcibedCountryUpdate(it)
        })
    }

    fun getAllCountryStats(lifecycleOwner: LifecycleOwner) {
        RepositoryImpl.liveSingleCountriesStatData.observe(lifecycleOwner, Observer {
            liveCountryStats.postValue(it)
        })
        RepositoryImpl.getCountriesData(lifecycleOwner)
    }

    fun getSharedPreferencesData(lifecycleOwner: LifecycleOwner){
        RepositoryImpl.liveSharedPreferencesData.observe(lifecycleOwner, Observer {
            liveSharedPreferencesData.postValue(it)
            oldSubscribedCountryData = SubscribedCountryData(it[1], it[2], "", "", it[3], "",it[4], "", null, "")
        })
        RepositoryImpl.getSharedPreferencesData()
    }

    fun setSharedPreferencesData(isCountrySubscribed:Boolean,countryName:String?){
        RepositoryImpl.setSharedPreferencesData(isCountrySubscribed,countryName,null,null,null)
    }

    fun onSubsrcibedCountryUpdate(newSubscribedCountryData: SubscribedCountryData){

        if(!(oldSubscribedCountryData?.cases.equals(newSubscribedCountryData.cases) &&
                    oldSubscribedCountryData?.deaths.equals(newSubscribedCountryData.deaths) &&
                    oldSubscribedCountryData?.totalRecovered.equals(newSubscribedCountryData.totalRecovered))){
            RepositoryImpl.setSharedPreferencesData(true,
                                                    newSubscribedCountryData.countryName,
                                                    newSubscribedCountryData.cases,
                                                    newSubscribedCountryData.deaths,
                                                    newSubscribedCountryData.totalRecovered)
            CovidNotification.pushNotification(newSubscribedCountryData)
        }
    }
}