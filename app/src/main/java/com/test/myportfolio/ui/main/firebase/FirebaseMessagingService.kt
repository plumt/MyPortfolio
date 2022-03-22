package com.test.myportfolio.ui.main.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.test.myportfolio.MainActivity
import com.test.myportfolio.PortfolioApplication
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.test.myportfolio.R

class FirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FirebaseServivce"

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.d("lys","onMR : ${p0.notification!!.body}")
        if (p0.notification != null) {
            sendNotification(p0.notification?.title, p0.notification!!.body!!)
        }
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("lys", "token : $p0")
    }


    private fun sendNotification(title: String?, message: String) {
        val CHANNEL_ID = "fcm_test"

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val CHANNEL_NAME = "fcm_push"
            val CHANNEL_DESCRIPTION = "push test"
            val importance = NotificationManager.IMPORTANCE_HIGH

            // add in API level 26
            val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            mChannel.description = CHANNEL_DESCRIPTION
            mChannel.enableLights(true)
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 100, 200)
            mChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationManagerCompat.createNotificationChannel(mChannel)
        }
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setContentTitle(title)
            builder.setVibrate(longArrayOf(500, 500))
        }
        notificationManagerCompat.notify((application as PortfolioApplication).id++, builder.build())

    }
}