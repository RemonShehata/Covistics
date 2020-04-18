package iti.intake40.covistics.core

import android.app.Application
import androidx.work.*
import java.util.concurrent.TimeUnit

class Base : Application() {
    override fun onCreate() {
        super.onCreate()
        CovidSharedPreferences.init(applicationContext)
        CovidNotification.init(applicationContext)
    }

    companion object {
        private val WORK_REQUEST_NAME = "COVID_UPDATE"

        fun enqueuePeriodicWorker(updateInterval: Long) {
            val updateRequest = PeriodicWorkRequest.Builder(
                CovidDataWorker::class.java, updateInterval,
                TimeUnit.MINUTES
            )
                .setConstraints(
                    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                )
                .build()
            val workManager = WorkManager.getInstance()
            workManager.enqueueUniquePeriodicWork(
                WORK_REQUEST_NAME,
                ExistingPeriodicWorkPolicy.REPLACE, updateRequest
            )
        }

        fun cancelPeriodicWorker() {
            val workManager = WorkManager.getInstance()
            workManager.cancelAllWork()
        }
    }
}