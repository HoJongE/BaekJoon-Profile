package per.hojong.baekjoonprofile.view

import android.app.Application
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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.ui.theme.BackgroundColor
import per.hojong.baekjoonprofile.ui.theme.BaekJoonProfileTheme
import per.hojong.baekjoonprofile.ui.theme.Gray
import per.hojong.baekjoonprofile.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel.getProfile("kiss5489")
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
        RoundTextField(8.dp, "ID", id, isError = loginViewModel.profileLoadingError)
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        RoundLoadingButton(
            value = stringResource(id = R.string.login),
            loading = loginViewModel.profileLoadingState,
            backgroundColor = BackgroundColor,
            disabledBackgroundColor = BackgroundColor
        ) {
            loginViewModel.getProfile(id = id.value)
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun RoundTextField(
    padding: Dp,
    title: String,
    value: MutableState<String>,
    isError: Boolean = false
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
    ContentView(LoginViewModel(LocalContext.current as Application))
}