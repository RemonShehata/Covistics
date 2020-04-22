package iti.intake40.covistics.core

import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.content.Intent
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import iti.intake40.covistics.R
import iti.intake40.covistics.data.RepositoryImpl
import iti.intake40.covistics.data.database.CountryRoomDatabase
import iti.intake40.covistics.data.model.SubscribedCountryData

class UpdateDataService : LifecycleService() {

    var oldSubscribedCountryData : SubscribedCountryData? = null

    override fun onCreate() {
        super.onCreate()
        startForeground(1,CovidNotification.serviceNotification())
        Log.d("Service","Done")
        val dao = CountryRoomDatabase.getDatabase(this.applicationContext).countryDao()
        RepositoryImpl.init(dao,this.applicationContext)
        RepositoryImpl.getDataFromAPI()

        RepositoryImpl.liveSubscribedCountryData.observe(this, Observer {
            onSubsrcibedCountryUpdate(it)
            Log.d("Service", it.toString())
        })

        RepositoryImpl.liveSharedPreferencesData.observe(this, Observer {
            Log.d("Service","changed")
            oldSubscribedCountryData =
                SubscribedCountryData(it[1], it[2], "", "", it[3], "", it[4], "", null, "")
        })
    }

    fun onSubsrcibedCountryUpdate(newSubscribedCountryData: SubscribedCountryData) {
        var newCases = 0
        var newDeaths = 0
        var newRecovered = 0

        if (!(oldSubscribedCountryData?.cases.equals(newSubscribedCountryData.cases) &&
                    oldSubscribedCountryData?.deaths.equals(newSubscribedCountryData.deaths) &&
                    oldSubscribedCountryData?.totalRecovered.equals(newSubscribedCountryData.totalRecovered))
        ) {
            if (!(oldSubscribedCountryData?.cases.isNullOrEmpty()
                        && oldSubscribedCountryData?.deaths.isNullOrEmpty()
                        && oldSubscribedCountryData?.totalRecovered.isNullOrEmpty())
            ) {
                newCases = newSubscribedCountryData.cases?.replace(",", "")?.toInt()!!  - oldSubscribedCountryData?.cases?.replace(",", "")?.toInt()!!

                newDeaths = newSubscribedCountryData.deaths?.replace(",", "")?.toInt()!! - oldSubscribedCountryData?.deaths?.replace(",", "")?.toInt()!!

                newRecovered = newSubscribedCountryData.totalRecovered?.replace(",", "")?.toInt()!! - oldSubscribedCountryData?.totalRecovered?.replace(",", "")?.toInt()!!
            }

            RepositoryImpl.setSharedPreferencesData(
                true,
                newSubscribedCountryData.countryName,
                newSubscribedCountryData.cases,
                newSubscribedCountryData.deaths,
                newSubscribedCountryData.totalRecovered
            )
            CovidNotification.pushNotification(
                newSubscribedCountryData,
                newCases,
                newDeaths,
                newRecovered
            )
        }
    }
}
