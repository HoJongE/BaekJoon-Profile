package per.hojong.baekjoonprofile.view.widget

import android.app.Activity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.AppWidgetTarget
import com.bumptech.glide.request.transition.Transition
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.data.putSolvedID
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.view.MainActivity

class WidgetBuilder(
    private val context: Context,
    private val appWidgetId: Int,
) {
    private var pendingIntent: PendingIntent? = null
    private var reloadPendingIntent: PendingIntent? = null
    private lateinit var profile: Profile
    private lateinit var appWidgetManager: AppWidgetManager
    fun Builder(): WidgetBuilder = apply {
        appWidgetManager = AppWidgetManager.getInstance(context)
    }


    fun profile(profile: Profile): WidgetBuilder = apply {
        this.profile = profile
    }


    fun build(initial: Boolean) {
        RemoteViews(context.packageName, R.layout.widget_baekjoon_profile).apply {
            setInt(
                R.id.linear_widget_container,
                "setBackgroundResource",
                Profile.getTierBackgroundId(tier = profile.tier)
            )
            setTextViewText(
                R.id.textview_profile_tier,
                Profile.getTierName(profile.tier) + " " + Profile.getTierNumber(profile.tier)
            )
            setViewVisibility(R.id.textview_profile_error, View.GONE)
            setTextViewText(R.id.textview_widget_profile_name, profile.handle)
            setTextViewText(
                R.id.textview_max_streak,
                profile.maxStreak.toString()
            )
            setTextViewText(R.id.textview_ranking, profile.rank.toString())
            setTextViewText(
                R.id.textview_solved_count,
                profile.solvedCount.toString()
            )

            val awt: AppWidgetTarget = object : AppWidgetTarget(
                context.applicationContext,
                R.id.imageview_profile,
                this,
                appWidgetId
            ) {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    super.onResourceReady(resource, transition)
                }
            }

            val options = RequestOptions().override(100, 100)
                .circleCrop()
                .placeholder(R.drawable.all_person)
                .error(R.drawable.all_error)

            context.applicationContext?.let { it1 ->
                Glide.with(it1).asBitmap()
                    .load(
                        profile.profileImageUrl
                            ?: "https://static.solved.ac/misc/360x360/default_profile.png"
                    )
                    .apply(options).into(awt)
            }

            pendingIntent?.let {
                setOnClickPendingIntent(
                    R.id.linear_widget_container,
                    it
                )
            }
            reloadPendingIntent?.let {
                setOnClickPendingIntent(
                    R.id.imagebutton_refresh,
                    it
                )
            }
        }.also {
            appWidgetManager.updateAppWidget(appWidgetId, it)
        }
        if (initial) {
            val resultValue = Intent().apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            putSolvedID(context = context, profile.handle, appWidgetId)
            (context as AppCompatActivity).apply {
                setResult(Activity.RESULT_OK, resultValue)
                finish()
            }
        }
    }

    fun errorWidgetBuild(e: Exception) {
        RemoteViews(context.packageName, R.layout.widget_baekjoon_profile).apply {
            setViewVisibility(R.id.textview_profile_error, View.VISIBLE)
            setTextViewText(R.id.textview_profile_tier, e.localizedMessage)
        }.also {
            appWidgetManager.updateAppWidget(appWidgetId, it)
        }
    }

    fun setActivityPendingIntent(): WidgetBuilder = apply {
        val pendingIntent: PendingIntent =
            Intent(context, MainActivity::class.java).apply {
                val profileID = profile.handle
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
        this.pendingIntent = pendingIntent
    }

    fun setReloadPendingIntent(): WidgetBuilder = apply {
        val pendingIntent: PendingIntent =
            Intent(context, MediumProfileWidgetProvider::class.java).apply {
                action = MediumProfileWidgetProvider.REFRESH_ACTION
                putExtra("appWidgetId", appWidgetId)
            }.let {
                PendingIntent.getBroadcast(
                    context,
                    appWidgetId,
                    it,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }
        this.reloadPendingIntent = pendingIntent
    }
}