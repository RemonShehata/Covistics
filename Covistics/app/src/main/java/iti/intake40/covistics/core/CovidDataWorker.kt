package iti.intake40.covistics.core

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import iti.intake40.covistics.data.RepositoryImpl
import iti.intake40.covistics.data.database.CountryRoomDatabase
import kotlinx.coroutines.delay


class CovidDataWorker(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    private val TAG = "RepositoryImpl"

    override fun doWork(): Result {
        Log.d(TAG, "WorkManager")
        RepositoryImpl.getDataFromAPI()
        return Result.success()
    }
}