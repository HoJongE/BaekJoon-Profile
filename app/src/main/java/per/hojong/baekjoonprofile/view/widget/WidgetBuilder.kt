package per.hojong.baekjoonprofile.view.widget

import android.app.Activity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import per.hojong.baekjoonprofile.view.profile.getTierImage

class WidgetBuilder(
    private val context: Context,
    private val appWidgetId: Int,
) {
    private var pendingIntent: PendingIntent? = null
    private lateinit var profile: Profile
    private lateinit var appWidgetManager: AppWidgetManager
    fun Builder(): WidgetBuilder = apply {
        appWidgetManager = AppWidgetManager.getInstance(context)
    }

    fun pendingIntent(pendingIntent: PendingIntent): WidgetBuilder = apply {
        this.pendingIntent = pendingIntent
    }

    fun profile(profile: Profile): WidgetBuilder = apply {
        this.profile = profile
    }


    fun build(initial: Boolean) {
        RemoteViews(context.packageName, R.layout.widget_baekjoon_profile).apply {
            if (profile.tier == 31) setViewVisibility(
                R.id.textview_tier_number,
                View.GONE
            )
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
            setImageViewResource(
                R.id.imageview_tier_badge,
                getTierImage(profile.tier)
            )
            setTextViewText(
                R.id.textview_tier_number,
                Profile.getTierNumber(profile.tier).toString()
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
                    .load(profile.profileImageUrl)
                    .apply(options).into(awt)
            }

            pendingIntent?.let {
                setOnClickPendingIntent(
                    R.id.linear_widget_container,
                    pendingIntent
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
}