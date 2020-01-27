package com.yazan98.culttrip.client.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yazan98.culttrip.client.R
import com.yazan98.culttrip.client.screen.MainScreen
import java.util.*

class DestinyNotification : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        println("Notification Attached SS")
        p0?.let {
            it.notification?.let {
                it.title?.let { it1 -> it.body?.let { it2 -> showNotification(it1, it2) } }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(title: String, body: String) {
        val intent = Intent(applicationContext, MainScreen::class.java)
        val CHANNEL_ID = applicationContext.getString(R.string.channel_notifications)
        val notificationChannel = NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_HIGH)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 1, intent, 0)
        val notification = Notification.Builder(applicationContext, CHANNEL_ID)
            .setContentText(title)
            .setContentTitle(body)
            .setContentIntent(pendingIntent)
            .setChannelId(CHANNEL_ID)
            .setSmallIcon(android.R.drawable.sym_action_chat)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(Random().nextInt(1000), notification)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

}
