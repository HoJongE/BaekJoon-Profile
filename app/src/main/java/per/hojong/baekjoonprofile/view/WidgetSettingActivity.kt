package per.hojong.baekjoonprofile.view

import android.appwidget.AppWidgetManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
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
        requestFocus()
        initData()
    }

    private fun requestFocus() {
        editText.post {
            val imm: InputMethodManager? =
                getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.showSoftInput(editText, 0)
        }
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
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }
        val button = findViewById<Button>(R.id.button_widget_create)
        button.setOnClickListener {
            createAppWidget(editText.text.toString())
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
                        .setActivityPendingIntent()
                        .setReloadPendingIntent()
                        .build(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}