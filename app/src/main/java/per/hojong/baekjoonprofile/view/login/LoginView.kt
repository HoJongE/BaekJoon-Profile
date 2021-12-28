package per.hojong.baekjoonprofile.view.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.network.ProfileLoadingState
import per.hojong.baekjoonprofile.ui.theme.BackgroundColor
import per.hojong.baekjoonprofile.ui.theme.Diamond
import per.hojong.baekjoonprofile.view.*

@ExperimentalComposeUiApi
@Composable
fun LoginView(
    profileLoadingState: ProfileLoadingState,
    profileProvider: (String) -> Unit
) {
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
            isError = profileLoadingState is ProfileLoadingState.Error,
            enable = profileLoadingState !is ProfileLoadingState.Loading
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        RoundLoadingButton(
            value = stringResource(id = R.string.profile_view),
            loading = profileLoadingState is ProfileLoadingState.Loading,
            backgroundColor = BackgroundColor,
            disabledBackgroundColor = BackgroundColor,
            textColor = Diamond
        ) {
            profileProvider(id.value)
        }
        if (profileLoadingState is ProfileLoadingState.Error) {
            ErrorText(error = "아이디 혹은 인터넷 연결 상태를 확인해주세요")
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LoginView(profileLoadingState = ProfileLoadingState.Loading) {

    }
}