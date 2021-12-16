package per.hojong.baekjoonprofile.view.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.RemoteViews
import androidx.annotation.ColorInt
import kotlinx.coroutines.*
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.data.getSolvedID
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.network.SolvedApiService
import per.hojong.baekjoonprofile.view.MainActivity
import per.hojong.baekjoonprofile.view.profile.getTierImage
import java.lang.Exception

class SmallProfileWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        context?.let {
            val profileName = getSolvedID(context = context)

            appWidgetIds?.forEach {
                val pendingIntent: PendingIntent =
                    Intent(context, MainActivity::class.java).let { intent ->
                        PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                    }

                val views: RemoteViews = RemoteViews(
                    context.packageName,
                    R.layout.small_widget_baekjoon_profile
                ).apply {
                    setOnClickPendingIntent(R.id.linear_widget_container, pendingIntent)
                }
                appWidgetManager?.updateAppWidget(it, views)
            }
            getRecentData(context, appWidgetManager, appWidgetIds, profileName)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    private fun getRecentData(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?,
        name: String
    ) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch(Dispatchers.IO) {
            val apiService = SolvedApiService.getInstance()
            try {
                val profile: Profile = apiService.getUserInfo(name)
                withContext(Dispatchers.Main) {
                    appWidgetIds?.forEach {
                        val views: RemoteViews = RemoteViews(
                            context?.packageName,
                            R.layout.small_widget_baekjoon_profile
                        ).apply {
                            setViewVisibility(R.id.textview_profile_update_error, View.GONE)
                            setTextViewText(R.id.textview_widget_profile_name, profile.handle)
                            setInt(
                                R.id.textview_tier,
                                "setBackgroundResource",
                                Profile.getTierColorId(profile.tier)
                            )
                            setInt(
                                R.id.textview_widget_profile_name,
                                "setBackgroundResource",
                                Profile.getTierColorId(profile.tier)
                            )
                            setInt(
                                R.id.textview_tier_number,
                                "setBackgroundResource",
                                Profile.getTierColorId(profile.tier)
                            )
                            setTextViewText(
                                R.id.textview_tier_number,
                                Profile.getTierNumber(profile.tier).toString()
                            )
                        }
                        appWidgetManager?.updateAppWidget(it, views)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    appWidgetIds?.forEach {
                        val views: RemoteViews = RemoteViews(
                            context?.packageName,
                            R.layout.widget_baekjoon_profile
                        ).apply {
                            setViewVisibility(R.id.textview_profile_update_error, View.VISIBLE)
                        }
                        appWidgetManager?.updateAppWidget(it, views)
                    }
                }
                e.printStackTrace()
            }
        }
    }
}