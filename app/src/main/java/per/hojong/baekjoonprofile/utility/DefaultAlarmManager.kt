package per.hojong.baekjoonprofile.utility

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import per.hojong.baekjoonprofile.view.widget.MediumProfileWidgetProvider
import java.util.*

class DefaultAlarmManager(
    private val context: Context,
) {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    companion object {
        const val DAY_CHANGE_CODE = 8888
        const val CHECK_SOLVED_CODE = 9999
    }

    fun setDayChangeAlarm(): DefaultAlarmManager = apply {
        val alarmIntent = Intent(context, MediumProfileWidgetProvider::class.java).let { intent ->
            intent.action = MediumProfileWidgetProvider.DAY_CHANGE_ALARM
            PendingIntent.getBroadcast(
                context,
                DAY_CHANGE_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
        }
        val calendar = Calendar.getInstance()
        if (calendar.get(Calendar.HOUR_OF_DAY) >= 0) {
            calendar.add(Calendar.DATE, 1)
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 10)
        calendar.set(Calendar.SECOND, 0)

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmIntent
        )
    }

    fun cancelDayChangeAlarm(): DefaultAlarmManager = apply {
        val alarmIntent = Intent(context, MediumProfileWidgetProvider::class.java).let { intent ->
            intent.action = MediumProfileWidgetProvider.DAY_CHANGE_ALARM
            PendingIntent.getBroadcast(
                context,
                DAY_CHANGE_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
        }
        alarmIntent.cancel()
        alarmManager.cancel(alarmIntent)
    }

    fun setCountCheckAlarm(): DefaultAlarmManager = apply {
        val alarmIntent = Intent(context, MediumProfileWidgetProvider::class.java).let { intent ->
            intent.action = MediumProfileWidgetProvider.CHECK_SOLVED_COUNT
            PendingIntent.getBroadcast(
                context,
                CHECK_SOLVED_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
        }

        val calendar = Calendar.getInstance()
        if (calendar.get(Calendar.HOUR_OF_DAY) >= 20) {
            calendar.add(Calendar.DATE, 1)
        }
        calendar.set(Calendar.HOUR_OF_DAY, 20)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmIntent
        )
    }

    fun cancelCountCheckAlarm(): DefaultAlarmManager = apply {
        val alarmIntent = Intent(context, MediumProfileWidgetProvider::class.java).let { intent ->
            intent.action = MediumProfileWidgetProvider.CHECK_SOLVED_COUNT
            PendingIntent.getBroadcast(
                context,
                CHECK_SOLVED_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
        }
        alarmIntent.cancel()
        alarmManager.cancel(alarmIntent)
    }
}