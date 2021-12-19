package per.hojong.baekjoonprofile.view

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.ui.theme.BackgroundColor
import per.hojong.baekjoonprofile.ui.theme.BaekJoonProfileTheme
import per.hojong.baekjoonprofile.ui.theme.Gray
import per.hojong.baekjoonprofile.viewmodel.LoginViewModel
import java.lang.Exception

class MainActivity : ComponentActivity() {
    val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.extras?.let {
            val id = it.get("id").toString()
            loginViewModel.getProfile(id)
        }
        setContent {
            BaekJoonProfileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold {
                        if (!loginViewModel.loginSuccess) {
                            ContentView(loginViewModel = loginViewModel)
                        } else {
                            DetailInfoView(
                                profile = loginViewModel.profileResponse!!,
                                loginViewModel = loginViewModel
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.extras?.let {
            val id = it.get("id").toString()
            loginViewModel.getProfile(id)
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun ContentView(loginViewModel: LoginViewModel) {
    LoginView(loginViewModel = loginViewModel)

}


@ExperimentalComposeUiApi
@Composable
fun LoginView(loginViewModel: LoginViewModel) {
    val id = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
        TitleText(value = stringResource(id = R.string.all_title))
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
        Image(
            painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxHeight(0.25f)
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        BodyText(value = stringResource(id = R.string.login_description))
        RoundTextField(
            8.dp,
            "ID",
            id,
            isError = loginViewModel.profileLoadingError,
            enable = !loginViewModel.profileLoadingState
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        RoundLoadingButton(
            value = stringResource(id = R.string.profile_view),
            loading = loginViewModel.profileLoadingState,
            backgroundColor = BackgroundColor,
            disabledBackgroundColor = BackgroundColor
        ) {
            loginViewModel.getProfile(id = id.value)
        }
        if (loginViewModel.profileLoadingError) {
            ErrorText(error = "아이디 혹은 인터넷 연결 상태를 확인해주세요")
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun RoundTextField(
    padding: Dp,
    title: String,
    value: MutableState<String>,
    isError: Boolean = false,
    enable: Boolean = true
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier =
        Modifier
            .fillMaxWidth(0.9f)
            .padding(padding)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = Gray
        )
        TextField(
            value = value.value,
            onValueChange = { value.value = it },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            enabled = enable,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.White,
                textColor = Color.White
            ),
            isError = isError,
            trailingIcon = {
                if (isError) {
                    Icon(Icons.Default.Warning, "Login Error", tint = MaterialTheme.colors.error)
                }
            }
        )
    }

}


@ExperimentalComposeUiApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    val application = Application()
    ContentView(LoginViewModel(application = application))
}
