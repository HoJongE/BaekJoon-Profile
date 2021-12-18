package per.hojong.baekjoonprofile.view

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.network.SolvedApiService
import per.hojong.baekjoonprofile.view.widget.WidgetBuilder

class WidgetSettingActivity : AppCompatActivity() {
    private var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var appWidgetManager: AppWidgetManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget_setting)
        setTextChangeListener()
        initData()
    }

    private fun setTextChangeListener() {
        editText = findViewById(R.id.edittext_widget_profile_id)
        button = findViewById(R.id.button_widget_create)
        editText.addTextChangedListener {
            if (it?.length == 0) {
                button.isEnabled = false
            } else if (it?.length ?: 0 > 0) {
                button.isEnabled = true
            }
        }
    }

    private fun initData() {
        appWidgetManager = AppWidgetManager.getInstance(this)
        appWidgetId = intent.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        val button = findViewById<Button>(R.id.button_widget_create)
        button.setOnClickListener {
            createAppWidget(editText.text.toString())
        }
    }

    private fun getPendingIntent(id: String): PendingIntent {
        return Intent(this, MainActivity::class.java).apply {
            val bundle = Bundle()
            bundle.putString("id", id)
            putExtras(bundle)
        }.let { intent ->
            PendingIntent.getActivity(
                this,
                appWidgetId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }

    private fun createAppWidget(id: String) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val apiService = SolvedApiService.getInstance()
            try {
                val profile: Profile =
                    apiService.getUserInfo(id)
                withContext(Dispatchers.Main) {
                    WidgetBuilder(this@WidgetSettingActivity, appWidgetId).Builder()
                        .profile(profile = profile)
                        .pendingIntent(getPendingIntent(id))
                        .build(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}