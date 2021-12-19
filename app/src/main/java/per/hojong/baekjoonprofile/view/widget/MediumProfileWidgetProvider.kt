package per.hojong.baekjoonprofile.view.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import per.hojong.baekjoonprofile.data.getSolvedID
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.network.SolvedApiService

class MediumProfileWidgetProvider : AppWidgetProvider() {
    companion object {
        const val REFRESH_ACTION = "ACTION_REFRESH"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let {
                if (it.action == REFRESH_ACTION) {
                    val appWidgetId =
                        it.getIntExtra("appWidgetId", AppWidgetManager.INVALID_APPWIDGET_ID)
                    if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                        getRecentData(context = context, appWidgetId = appWidgetId)
                        Log.d("widget", "receive Update")
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
                Log.d("widget", "update")
            }
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }


    private fun getRecentData(
        context: Context,
        appWidgetId: Int
    ) {
        val scope = CoroutineScope(Dispatchers.IO)

        scope.launch {
            val apiService = SolvedApiService.getInstance()
            try {
                val profile: Profile =
                    apiService.getUserInfo(getSolvedID(context = context, appWidgetId))
                Log.d("widget", profile.handle)
                withContext(Dispatchers.Main) {
                    WidgetBuilder(context = context, appWidgetId = appWidgetId).Builder()
                        .profile(profile = profile)
                        .setActivityPendingIntent()
                        .setReloadPendingIntent()
                        .build(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}