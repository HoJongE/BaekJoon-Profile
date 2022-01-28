package per.hojong.baekjoonprofile.view.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import per.hojong.baekjoonprofile.data.*
import per.hojong.baekjoonprofile.data.source.ProfileRemoteSource
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.utility.DefaultAlarmManager
import per.hojong.baekjoonprofile.utility.DefaultNotificationManager
import per.hojong.baekjoonprofile.utility.NetworkCoroutine

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class MediumProfileWidgetProvider : AppWidgetProvider() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    companion object {
        const val REFRESH_ACTION = "ACTION_REFRESH"
        const val DAY_CHANGE_ALARM = "DAY_CHANGE_ALARM"
        const val CHECK_SOLVED_COUNT = "CHECK_SOLVE_COUNT"
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let { intent ->
                when (intent.action) {
                    REFRESH_ACTION -> {
                        val appWidgetId =
                            intent.getIntExtra("appWidgetId", AppWidgetManager.INVALID_APPWIDGET_ID)
                        if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                            getRecentData(
                                context = context,
                                appWidgetId = appWidgetId,
                                makeToast = true
                            )
                        }
                    }
                    Intent.ACTION_BOOT_COMPLETED -> {
                        setAlarm(it)
                    }
                    DAY_CHANGE_ALARM -> {
                        getRecentData(
                            it, widgetBuild = false,
                            solvedID = getDefaultSolvedID(it)
                        ) { profile ->
                            putTodaySolvedCount(it, profile.solvedCount)
                        }
                    }
                    CHECK_SOLVED_COUNT -> {
                        getRecentData(
                            it, widgetBuild = false,
                            solvedID = getDefaultSolvedID(it)
                        ) { profile ->
                            if (getTodaySolvedCount(it) == profile.solvedCount) {
                                DefaultNotificationManager(it)
                                    .showNotification(
                                        profile.handle,
                                        "${profile.handle}님,오늘 문제를 풀지 않았습니다!",
                                        "스트릭이 끊기기 전에 문제를 풀어주세요!"
                                    )
                            }
                        }
                        setAlarm(it)
                    }
                }
            }
        }
        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        context?.let {
            appWidgetIds?.forEach {
                getRecentData(context, it)
            }
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }


    private fun getRecentData(
        context: Context,
        appWidgetId: Int = 0,
        solvedID: String? = null,
        makeToast: Boolean = false,
        widgetBuild: Boolean = true,
        onSuccess: ((Profile) -> Unit)? = null
    ) {
        val networkCoroutine = NetworkCoroutine()
        networkCoroutine.getCoroutineScope().launch {
            val apiService = ProfileRemoteSource.getInstance()
            val profile: Profile =
                apiService.getUserInfo(solvedID ?: getSolvedID(context = context, appWidgetId))
            withContext(networkCoroutine.uiDispatcher) {
                if (widgetBuild) {
                    WidgetBuilder(context = context, appWidgetId = appWidgetId).builder()
                        .profile(profile = profile)
                        .setActivityPendingIntent()
                        .setReloadPendingIntent()
                        .build(false)
                }
                if (makeToast) {
                    Toast.makeText(context, "${profile.handle} 업데이트 완료!", Toast.LENGTH_SHORT).show()
                }
                onSuccess?.invoke(profile)
            }
        }
    }

    override fun onEnabled(context: Context?) {
        context?.let {
            setAlarm(it)
        }
        super.onEnabled(context)
    }


    override fun onDisabled(context: Context?) {
        context?.let {
            clear(it)
            cancelAlarm(it)
        }
        super.onDisabled(context)
    }


    private fun setAlarm(context: Context) {
        DefaultAlarmManager(context = context)
            .setDayChangeAlarm()
            .setCountCheckAlarm()
    }

    private fun cancelAlarm(context: Context) {
        DefaultAlarmManager(context)
            .cancelCountCheckAlarm()
            .cancelDayChangeAlarm()
    }


}