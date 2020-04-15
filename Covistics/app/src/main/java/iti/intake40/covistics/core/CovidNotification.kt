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

object CovidNotification {

    private val CHANNEL_NAME = (R.string.app_name).toString()
    private val CHANNEL_DESC = "COVID Notification Channel"

    fun pushNotification(context: Context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotficationChannel(context)
        }
        createNotification(context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotficationChannel(context:Context){
            val channelId = "${context.packageName}-${CHANNEL_NAME}"
            val channel =  NotificationChannel(channelId,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESC
            channel.setShowBadge(false)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(context: Context){
        val channelId = "${context.packageName}-${CHANNEL_NAME}"
        val notification = NotificationCompat.Builder(context,channelId)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Egypt")
                    .setContentText("BlaBla")
                    .setStyle(NotificationCompat.BigTextStyle().bigText("BLABLA"))
                    .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                    .setAutoCancel(true)
                    .build()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(0,notification)
    }
}