package per.hojong.baekjoonprofile.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.ui.theme.BaekJoonProfileTheme
import per.hojong.baekjoonprofile.ui.theme.Gray

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContentView()
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun ContentView() {
    BaekJoonProfileTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            LoginView()
        }
    }

}

@ExperimentalComposeUiApi
@Composable
fun LoginView() {
    val id = remember {
        mutableStateOf("")
    }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState
    ) {
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
            RoundTextField(8.dp, "ID", id)
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            RoundButton(null, value = stringResource(id = R.string.login)) {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        "버튼 클릭!"
                    )
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun RoundTextField(padding: Dp, title: String, value: MutableState<String>) {
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
            })
        )
    }

}


@ExperimentalComposeUiApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ContentView()
}