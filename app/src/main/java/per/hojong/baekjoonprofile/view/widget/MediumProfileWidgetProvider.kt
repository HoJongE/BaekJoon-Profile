package per.hojong.baekjoonprofile.view.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.RemoteViews
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.AppWidgetTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.data.getSolvedID
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.network.SolvedApiService
import per.hojong.baekjoonprofile.view.MainActivity
import per.hojong.baekjoonprofile.view.profile.getTierImage
import java.lang.Exception

class MediumProfileWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        context?.let {
            val profileName = getSolvedID(context)
            appWidgetIds?.forEach {
                val pendingIntent: PendingIntent =
                    Intent(context, MainActivity::class.java).let { intent ->
                        PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                    }

                val views: RemoteViews = RemoteViews(
                    context.packageName,
                    R.layout.widget_baekjoon_profile
                ).apply {
                    setOnClickPendingIntent(R.id.linear_widget_container, pendingIntent)
                }
                appWidgetManager?.updateAppWidget(it, views)
            }
            getRecentData(context, appWidgetManager, appWidgetIds, name = profileName ?: "")
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

        scope.launch {
            val apiService = SolvedApiService.getInstance()
            try {
                val profile: Profile = apiService.getUserInfo(name)
                withContext(Dispatchers.Main) {
                    appWidgetIds?.forEach {
                        val views: RemoteViews = RemoteViews(
                            context?.packageName, R.layout.widget_baekjoon_profile
                        ).apply {
                            setViewVisibility(R.id.textview_profile_update_error, View.GONE)
                            if (profile.tier == 31) setViewVisibility(
                                R.id.textview_tier_number,
                                View.GONE
                            )
                            setTextViewText(R.id.textview_widget_profile_name, profile.handle)
                            setTextViewText(R.id.textview_max_streak, profile.maxStreak.toString())
                            setTextViewText(R.id.textview_ranking, profile.rank.toString())
                            setTextViewText(
                                R.id.textview_solved_count,
                                profile.solvedCount.toString()
                            )
                            setImageViewResource(
                                R.id.imageview_tier_badge,
                                getTierImage(profile.tier)
                            )
                            setTextViewText(
                                R.id.textview_tier_number,
                                Profile.getTierNumber(profile.tier).toString()
                            )
                            val awt: AppWidgetTarget = object : AppWidgetTarget(
                                context?.applicationContext,
                                R.id.imageview_profile,
                                this,
                                *appWidgetIds
                            ) {
                                override fun onResourceReady(
                                    resource: Bitmap,
                                    transition: Transition<in Bitmap>?
                                ) {
                                    super.onResourceReady(resource, transition)
                                }
                            }

                            var options = RequestOptions().override(100, 100)
                                .circleCrop()
                                .placeholder(R.drawable.all_person)
                                .error(R.drawable.all_error)

                            context?.applicationContext?.let { it1 ->
                                Glide.with(it1).asBitmap()
                                    .load(profile.profileImageUrl)
                                    .apply(options).into(awt)
                            }
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