package per.hojong.baekjoonprofile.utility

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.view.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class DefaultNotificationManager(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val CHANNEL_ID = "Solve Channel"
        const val CLICK_REQUEST = 5000
    }

    init {
        createNotificationChannel()
    }

    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    fun showNotification(id: String, title: String, content: String, largeIcon: Bitmap? = null) {
        val dateFormat = SimpleDateFormat("MMddHHmmss")
        val notiId = Integer.parseInt(dateFormat.format(Date()))
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(title)
            .setContentText(content)
            .setLargeIcon(largeIcon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(createPendingIntent(id))

        with(NotificationManagerCompat.from(context)) {
            notify(notiId, builder.build())
        }
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    private fun createPendingIntent(id: String): PendingIntent =
        Intent(
            context,
            MainActivity::class.java
        ).apply {
            val bundle = Bundle()
            bundle.putString("id", id)
            this.putExtras(bundle)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }.let { intent ->
            PendingIntent.getActivity(
                context, CLICK_REQUEST, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }


    private fun createNotificationChannel() {
        //API 레벨 26이상일 때만 채널을 생성함
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.description_text)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}