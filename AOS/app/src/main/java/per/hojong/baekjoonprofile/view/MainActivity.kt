package per.hojong.baekjoonprofile.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import per.hojong.baekjoonprofile.network.ProfileLoadingState
import per.hojong.baekjoonprofile.ui.theme.BaekJoonProfileTheme
import per.hojong.baekjoonprofile.viewmodel.LoginViewModel

@AndroidEntryPoint
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            ContentView(
                navHostController = navController,
                loginViewModel = loginViewModel
            )
        }
        loadBundle(intent = intent)
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
            loginViewModel.logout()
            loginViewModel.getProfile(id)
        }
    }
}

@ExperimentalComposeUiApi
@Composable
@ExperimentalAnimationApi
fun ContentView(loginViewModel: LoginViewModel, navHostController: NavHostController) {
    BaekJoonProfileTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold {
                AnimatedNavHost(
                    navController = navHostController,
                    startDestination = NavRoute.LOGIN
                ) {
                    loginComposable(loginViewModel = loginViewModel) {
                        val json = Uri.encode(Gson().toJson(it))
                        navHostController.navigate(
                            route = NavRoute.INFO.replace("{profile}", json)
                        ) {
                            launchSingleTop = true
                        }
                    }
                    infoComposable {
                        loginViewModel.logout()
                        navHostController.navigate(NavRoute.LOGIN) {
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}