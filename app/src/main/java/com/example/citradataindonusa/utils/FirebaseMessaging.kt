package com.example.citradataindonusa.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.citradataindonusa.ui.MainActivity
import com.example.core_ui.R
import com.example.domain.preferences.UserPreferences
import com.example.domain.usecase.authentication.SaveTokenUseCase
import com.example.domain.usecase.notification.SyncNotificationUseCase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class FirebaseMessaging : FirebaseMessagingService() {

    @Inject
    lateinit var syncNotificationUseCase: SyncNotificationUseCase

    @Inject
    lateinit var userPreferences: UserPreferences

    @Inject
    lateinit var saveTokenUseCase: SaveTokenUseCase


    override fun onNewToken(token: String) {
        super.onNewToken(token)

        CoroutineScope(Dispatchers.IO).launch {
            val session = userPreferences.getSession().first()
            if (session.isLogin && session.idUser.isNotEmpty()) {
                try {
                    saveTokenUseCase(session.idUser, token)
                } catch (e: Exception) {
                    Timber.tag("e").d("$e ,Failed to sync FCM token")
                }
            }
        }

        FirebaseMessaging.getInstance().token
            .addOnSuccessListener {
                Timber.tag("FCM").d("Current Token = $it")
            }
            .addOnFailureListener {
                Timber.tag("FCM").d("ERROR =  $it")
            }

    }

    //jika pesan berisi payload data
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data

        // Extract data
        val id = data["id"]?.toIntOrNull()
        val title = data["title"] ?: message.notification?.title ?: ""
        val body = data["body"] ?: message.notification?.body ?: ""
        val projectId = data["project_id"]



        if (id != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val session = userPreferences.getSession().first()
                if (session.isLogin){
                    syncNotificationUseCase(session.idUser)
                }
            }
        }
        // NOTIFICATION SUDAH DITAMPILKAN OTOMATIS OLEH SISTEM
        // Hanya tampilkan manual jika ini adalah data message (tanpa notification)
        if (message.notification == null) {
            showNotification(title, body, projectId)
        }

    }

    private fun showNotification(title: String, message: String, projectId: String? = null) {
        val channelId = "default_channel"

        // Verifikasi channel exists
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = notificationManager.getNotificationChannel(channelId)
        if (channel == null) {
            return
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP

            putExtra("project_id", projectId)
            putExtra("from_notification", true)
        }

        val requestCode = System.currentTimeMillis().toInt()

        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
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





