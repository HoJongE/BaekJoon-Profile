package per.hojong.baekjoonprofile.view

import android.appwidget.AppWidgetManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.network.ProfileLoadingState
import per.hojong.baekjoonprofile.view.widget.WidgetBuilder
import per.hojong.baekjoonprofile.viewmodel.LoginViewModel

@AndroidEntryPoint
class WidgetSettingActivity : AppCompatActivity() {
    private var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID
    private val mLoginViewModel: LoginViewModel by viewModels()
    private val editText: EditText by lazy {
        findViewById(R.id.edittext_widget_profile_id)
    }
    private val textInputLayout: TextInputLayout by lazy {
        findViewById(R.id.textinputlayout)
    }
    private val button: Button by lazy {
        findViewById(R.id.button_widget_create)
    }
    private lateinit var appWidgetManager: AppWidgetManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget_setting)
        setTextChangeListener()
        setObserver()
        requestFocus()
        initData()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            mLoginViewModel.profileLoadingState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is ProfileLoadingState.Loading -> showLoadingView()
                        is ProfileLoadingState.Error -> showError()
                        else -> {}
                    }
                }
        }
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

    private fun showLoadingView() {
        button.isEnabled = false
    }

    private fun hideLoadingView() {
        button.isEnabled = true
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
            hideError()
            createAppWidget(editText.text.toString())
        }
    }

    private fun createAppWidget(id: String) {
        mLoginViewModel.getProfile(id) {
            WidgetBuilder(this@WidgetSettingActivity, appWidgetId).builder()
                .profile(it)
                .setActivityPendingIntent()
                .setReloadPendingIntent()
                .build(true)
        }
    }

    private fun hideError() {
        textInputLayout.error = null
    }

    private fun showError() {
        hideLoadingView()
        textInputLayout.error = "아이디나 네트워크 상태를\n확인해주세요"
    }
}