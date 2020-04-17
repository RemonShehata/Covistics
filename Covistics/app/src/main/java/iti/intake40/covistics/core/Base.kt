package iti.intake40.covistics.core

import android.app.Application
import androidx.work.*
import java.util.concurrent.TimeUnit

class Base : Application() {
    private val WORK_REQUEST_NAME = "COVID_UPDATE"
    override fun onCreate() {
        super.onCreate()
        CovidSharedPreferences.init(applicationContext)
        CovidNotification.init(applicationContext)
        val updateRequest =  PeriodicWorkRequest.Builder(CovidDataWorker::class.java,15,
            TimeUnit.MINUTES)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .build()
        val workManager = WorkManager.getInstance()
        workManager.enqueueUniquePeriodicWork(WORK_REQUEST_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,updateRequest)
    }
}