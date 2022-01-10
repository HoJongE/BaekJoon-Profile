package per.hojong.baekjoonprofile.view.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import per.hojong.baekjoonprofile.data.getSolvedID
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.data.source.ProfileRemoteSource
import per.hojong.baekjoonprofile.utility.NetworkCoroutine

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
                        getRecentData(
                            context = context,
                            appWidgetId = appWidgetId,
                            makeToast = true
                        )
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
            }
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }


    private fun getRecentData(
        context: Context,
        appWidgetId: Int,
        makeToast: Boolean = false
    ) {
        val networkCoroutine = NetworkCoroutine()
        networkCoroutine.getCoroutineScope().launch {
            val apiService = ProfileRemoteSource.getInstance()
            val profile: Profile =
                apiService.getUserInfo(getSolvedID(context = context, appWidgetId))
            withContext(networkCoroutine.uiDispatcher) {
                WidgetBuilder(context = context, appWidgetId = appWidgetId).Builder()
                    .profile(profile = profile)
                    .setActivityPendingIntent()
                    .setReloadPendingIntent()
                    .build(false)
                if (makeToast) {
                    Toast.makeText(context, "${profile.handle} 업데이트 완료!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}