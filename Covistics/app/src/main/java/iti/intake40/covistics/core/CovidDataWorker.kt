package iti.intake40.covistics.core

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.work.Worker
import androidx.work.WorkerParameters
import iti.intake40.covistics.data.RepositoryImpl
import iti.intake40.covistics.data.database.CountryRoomDatabase


class CovidDataWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    private val TAG = "RepositoryImpl"

    override fun doWork(): Result {
        Log.d(TAG, "WorkManager")
        val serviceIntent = Intent(applicationContext,UpdateDataService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            applicationContext.startForegroundService(serviceIntent)
        }else{
            applicationContext.startService(serviceIntent)
        }
        val dao = CountryRoomDatabase.getDatabase(applicationContext).countryDao()
        RepositoryImpl.getDataFromAPI()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(Runnable { // Run your task here
            //Toast.makeText(applicationContext, "Testing", Toast.LENGTH_SHORT).show()
        }, 1000)

        return Result.success()
    }
}