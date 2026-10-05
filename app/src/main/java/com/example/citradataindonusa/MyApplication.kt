package com.example.citradataindonusa

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
       if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())   // <- WAJIB ada baris ini
        }
        checkNotificationChannel()


    }

    private fun checkNotificationChannel() {
        val channelId = "default_channel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = notificationManager.getNotificationChannel(channelId)

        if (channel != null) {
            Timber.tag("Channel exists").d("${channel.id} - ${channel.name}")
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