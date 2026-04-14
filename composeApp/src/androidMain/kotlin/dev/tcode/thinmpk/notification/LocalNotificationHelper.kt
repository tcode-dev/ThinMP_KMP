package dev.tcode.thinmpk.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.session.MediaStyleNotificationHelper
import dev.tcode.thinmpk.constant.NotificationConstant

object LocalNotificationHelper {
    fun notify(notification: Notification, context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(NotificationConstant.NOTIFICATION_ID, notification)
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun createNotification(
        context: Context,
        mediaStyle: MediaStyleNotificationHelper.MediaStyle,
        title: String,
        message: String,
        albumArtBitmap: Bitmap?
    ): Notification {
        val builder = NotificationCompat.Builder(context, NotificationConstant.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setStyle(mediaStyle)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (albumArtBitmap != null) {
            builder.setLargeIcon(albumArtBitmap)
        }

        return builder.build()
    }

    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            NotificationConstant.CHANNEL_ID,
            "Music Player",
            NotificationManager.IMPORTANCE_LOW
        )
        val notificationManager = context.getSystemService(NotificationManager::class.java)

        notificationManager.createNotificationChannel(channel)
    }

    fun cancelAll(context: Context) {
        NotificationManagerCompat.from(context).cancelAll()
    }
}