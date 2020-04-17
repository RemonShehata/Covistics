package iti.intake40.covistics.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.TypedArrayUtils
import iti.intake40.covistics.R
import iti.intake40.covistics.data.model.SubscribedCountryData

object CovidNotification {

    private val CHANNEL_NAME = (R.string.app_name).toString()
    private val CHANNEL_DESC = "COVID Notification Channel"
    private lateinit var context: Context
    private lateinit var channelId : String

    fun init(context: Context){
        this.context = context
        channelId = "${context.packageName}-${CHANNEL_NAME}"
    }

    fun pushNotification(countryData:SubscribedCountryData,newCases:Int,newDeaths:Int,newRecovered:Int){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotficationChannel()
        }
        createNotification(countryData,newCases,newDeaths,newRecovered)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotficationChannel(){
            val channel =  NotificationChannel(channelId,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESC
            channel.setShowBadge(false)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(countryData:SubscribedCountryData,newCases:Int,newDeaths:Int,newRecovered: Int){
        val notification = NotificationCompat.Builder(context,channelId)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(countryData.countryName)
                    .setStyle(NotificationCompat.BigTextStyle().bigText("Total Cases: ${countryData.cases} New Cases: ${newCases}\n" +
                                                                             "Total deaths: ${countryData.deaths} New Deaths: ${newDeaths}\n" +
                                                                              "Total Recovered: ${countryData.totalRecovered} New Recovered: ${newRecovered}"))
                    .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                    .setAutoCancel(true)
                    .build()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(0,notification)
    }
}