package per.hojong.baekjoonprofile.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import per.hojong.baekjoonprofile.network.ProfileLoadingState
import per.hojong.baekjoonprofile.ui.theme.BaekJoonProfileTheme
import per.hojong.baekjoonprofile.view.login.LoginView
import per.hojong.baekjoonprofile.viewmodel.LoginViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadBundle(intent = intent)

        setContent {
            ContentView(loginViewModel = loginViewModel)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        loadBundle(intent = intent)
    }

    /**
     * if intent is not null, get id from bundle
     * and login automatically
     *
     * @param intent intent
     */
    private fun loadBundle(intent: Intent?) {
        intent?.extras?.let {
            val id = it.get("id").toString()
            loginViewModel.getProfile(id)
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun ContentView(loginViewModel: LoginViewModel) {
    val profileLoadingState: ProfileLoadingState by
    loginViewModel.profileLoadingState.collectAsState()
    BaekJoonProfileTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold {
                when (profileLoadingState) {
                    is ProfileLoadingState.Empty,
                    is ProfileLoadingState.Loading,
                    is ProfileLoadingState.Error -> LoginView(
                        profileLoadingState = profileLoadingState,
                        profileProvider = loginViewModel::getProfile
                    )
                    is ProfileLoadingState.Success -> DetailInfoView(
                        profile = (profileLoadingState as ProfileLoadingState.Success).profile,
                        loginViewModel::logout
                    )
                }
            }
        }
    }
}




