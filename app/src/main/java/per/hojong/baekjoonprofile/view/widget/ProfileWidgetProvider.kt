package per.hojong.baekjoonprofile.view.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.RemoteViews
import kotlinx.coroutines.*
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.network.SolvedApiService
import per.hojong.baekjoonprofile.view.MainActivity
import per.hojong.baekjoonprofile.view.profile.getTierImage
import java.lang.Exception

class ProfileWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        appWidgetIds?.forEach {
            val pendingIntent: PendingIntent =
                Intent(context, MainActivity::class.java).let { intent ->
                    PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                }

            val views: RemoteViews = RemoteViews(
                context?.packageName,
                R.layout.widget_baekjoon_profile
            ).apply {
                setOnClickPendingIntent(R.id.linear_widget_container, pendingIntent)
            }
            appWidgetManager?.updateAppWidget(it, views)
        }
        getRecentData(context, appWidgetManager, appWidgetIds)
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    private fun getRecentData(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        GlobalScope.launch {
            val apiService = SolvedApiService.getInstance()
            try {
                val profile: Profile = apiService.getUserInfo("as00098")
                appWidgetIds?.forEach {
                    val views: RemoteViews = RemoteViews(
                        context?.packageName,
                        R.layout.widget_baekjoon_profile
                    ).apply {
                        setTextViewText(R.id.textview_widget_profile_name, profile.handle)
                        setTextViewText(R.id.textview_max_streak, profile.maxStreak.toString())
                        setTextViewText(R.id.textview_ranking, profile.rank.toString())
                        setTextViewText(R.id.textview_solved_count, profile.solvedCount.toString())
                        setImageViewResource(R.id.imageview_tier_badge, getTierImage(profile.tier))
                        setTextViewText(R.id.textview_tier_number, Profile.getTierNumber(profile.tier).toString())
                    }
                    appWidgetManager?.updateAppWidget(it, views)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}