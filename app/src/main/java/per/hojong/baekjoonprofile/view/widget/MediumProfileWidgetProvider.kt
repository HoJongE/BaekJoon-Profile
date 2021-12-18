package per.hojong.baekjoonprofile.view.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import per.hojong.baekjoonprofile.data.getSolvedID
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.network.SolvedApiService
import per.hojong.baekjoonprofile.view.MainActivity

class MediumProfileWidgetProvider : AppWidgetProvider() {
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

    private fun getPendingIntent(context: Context, appWidgetId: Int): PendingIntent {
        val pendingIntent: PendingIntent =
            Intent(context, MainActivity::class.java).apply {
                val profileID = getSolvedID(context = context, appWidgetId)
                val bundle = Bundle()
                bundle.putString("id", profileID)
                this.putExtras(bundle)
            }.let { intent ->
                PendingIntent.getActivity(
                    context,
                    appWidgetId,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
        return pendingIntent
    }

    private fun getRecentData(
        context: Context,
        appWidgetId: Int
    ) {
        val scope = CoroutineScope(Dispatchers.IO)

        scope.launch {
            val apiService = SolvedApiService.getInstance()
            val pendingIntent = getPendingIntent(context = context, appWidgetId = appWidgetId)
            try {
                val profile: Profile =
                    apiService.getUserInfo(getSolvedID(context = context, appWidgetId))
                withContext(Dispatchers.Main) {
                    WidgetBuilder(context = context, appWidgetId = appWidgetId).Builder()
                        .pendingIntent(pendingIntent = pendingIntent)
                        .profile(profile = profile)
                        .build(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}