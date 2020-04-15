package iti.intake40.covistics.viewmodel

import android.app.Application
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
        RepositoryImpl.getSubscribedCountryDataFromAPI()
    }

    fun getAllCountryStats(lifecycleOwner: LifecycleOwner) {
        RepositoryImpl.liveSingleCountriesStatData.observe(lifecycleOwner, Observer {
            liveCountryStats.postValue(it)
        })
        RepositoryImpl.getCountriesData(lifecycleOwner)
    }

    fun onSubsrcibedCountryUpdate(newSubscribedCountryData: SubscribedCountryData){
        val oldSubscribedCountryData = SubscribedCountryData("Egypt", "2,350", "160", "1,583",
                                    "178", "14", "589", "", null, "23")
//        val newSubscribedCountryData = SubscribedCountryData("Egypt", "200", "15", "150",
//            "250", "14", "350", "", "", "")

        if(oldSubscribedCountryData == newSubscribedCountryData){
            Log.d("Eqality","EQUAL")
        }else{
            Log.d("Eqality","NOT EQUAL")
            CovidNotification.pushNotification(getApplication())
        }
    }

}