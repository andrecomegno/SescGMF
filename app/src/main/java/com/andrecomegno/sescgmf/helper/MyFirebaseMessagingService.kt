package com.andrecomegno.sescgmf.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.andrecomegno.sescgmf.R
import com.andrecomegno.sescgmf.authentication.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: ${remoteMessage.from}")

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.body!!)
        }

        remoteMessage.data.isNotEmpty().let {
            val action = remoteMessage.data["action"]
            if (action == "logout") {
                // REALIZA O LOGOUT
                FirebaseAuth.getInstance().signOut()

                // NAVEGAR PARA A TELA DE LOGIN
                val intent = Intent(this, Login::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
        saveTokenToDatabase(token)
    }

    private fun saveTokenToDatabase(token: String) {
        val userId = FirebaseHelper.getIdUser()
        userId?.let {
            val databaseReference = FirebaseHelper.getDatabase().child("academy").child("gym_user").child("user_data").child(it)
            databaseReference.child("token").setValue(token)
        }
    }

    private fun sendNotification(messageBody: String) {
        // CONTADOR DO BADGE
        val currentCount = getBadgeCount()
        setBadgeCount(currentCount + 1)

        val channelId = "default_channel_id"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(messageBody)
            .setAutoCancel(true)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun getBadgeCount(): Int {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("badge_count", 0)
    }

    private fun setBadgeCount(count: Int) {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("badge_count", count).apply()
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}