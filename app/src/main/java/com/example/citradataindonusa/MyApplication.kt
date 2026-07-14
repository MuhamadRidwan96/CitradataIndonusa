package com.example.citradataindonusa

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        checkNotificationChannel()
    }

    private fun checkNotificationChannel() {
        val channelId = "default_channel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = notificationManager.getNotificationChannel(channelId)

        if (channel != null) {
            Log.d("Channel exists", "${channel.id} - ${channel.name}")
        } else {
            // Force create channel jika tidak ditemukan
            createNotificationChannel()
        }
    }


    private fun createNotificationChannel() {
        val channelId = "default_channel"
        val channelName = "General Notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = "Channel for all notifications"
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}