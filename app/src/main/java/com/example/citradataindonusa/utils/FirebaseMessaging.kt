package com.example.citradataindonusa.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.citradataindonusa.ui.MainActivity
import com.example.core_ui.R
import com.example.domain.model.NotificationModel
import com.example.domain.usecase.notification.UpdateNotificationCountUseCase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseMessaging : FirebaseMessagingService() {

    @Inject
    lateinit var updateNotificationCountUseCase: UpdateNotificationCountUseCase

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    //jika pesan berisi payload data
    override fun onMessageReceived(message: RemoteMessage) {

        val title = message.notification?.title ?:
                    message.data["title"] ?: "New message"
        val body =  message.notification?.body ?:
                    message.data["body"] ?: "You have a new notification"
        showNotification(title, body)

        CoroutineScope(Dispatchers.IO).launch {
            updateNotificationCountUseCase(
                NotificationModel(
                    id = 0,
                    title = title,
                    body = body,
                    isRead = false,
                    timestamp = System.currentTimeMillis()
                )
            )
        }


    }


    private fun showNotification(title: String, message: String, projectId: String? = null) {
        val channelId = "default_channel"

        // Verifikasi channel exists
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = notificationManager.getNotificationChannel(channelId)
            if (channel == null) {
                return
            }
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("project_id", projectId)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_SOUND)
            .setContentIntent(pendingIntent)


        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

}