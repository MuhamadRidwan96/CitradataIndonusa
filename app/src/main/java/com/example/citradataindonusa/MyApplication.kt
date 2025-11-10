package com.example.citradataindonusa

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import io.kotzilla.sdk.analytics.koin.analytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        checkNotificationChannel()

        CoroutineScope(Dispatchers.Default).launch {
            FirebaseApp.initializeApp(this@MyApplication)
        }

        startKoin {
            androidContext(this@MyApplication)

            // Add kotzilla analytics
            analytics()
        }

    }

    private fun checkNotificationChannel() {
        val channelId = "default_channel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = notificationManager.getNotificationChannel(channelId)

        if (channel != null) {
            Timber.d("Channel exists: ${channel.id} - ${channel.name}")
        } else {
            Timber.e("Channel NOT found: $channelId")
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
        Timber.d("Channel created: $channelId")
    }
}