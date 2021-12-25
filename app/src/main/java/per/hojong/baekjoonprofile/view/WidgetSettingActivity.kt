package per.hojong.baekjoonprofile.view

import android.appwidget.AppWidgetManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.network.SolvedApiService
import per.hojong.baekjoonprofile.utility.NetworkCoroutine
import per.hojong.baekjoonprofile.view.widget.WidgetBuilder
import per.hojong.baekjoonprofile.viewmodel.LoginViewModel

@AndroidEntryPoint
class WidgetSettingActivity : AppCompatActivity() {
    private var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID
    private val loginViewModel: LoginViewModel by viewModels()
    private val editText: EditText by lazy {
        findViewById(R.id.edittext_widget_profile_id)
    }
    private val button: Button by lazy {
        findViewById(R.id.button_widget_create)
    }
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
        loginViewModel.getProfile(id) {
            WidgetBuilder(this@WidgetSettingActivity, appWidgetId).Builder()
                .profile(it)
                .setActivityPendingIntent()
                .setReloadPendingIntent()
                .build(true)
        }
    }
}