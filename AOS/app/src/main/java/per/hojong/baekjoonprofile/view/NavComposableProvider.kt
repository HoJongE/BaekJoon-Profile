package per.hojong.baekjoonprofile.view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.model.ProfileType
import per.hojong.baekjoonprofile.view.login.LoginView
import per.hojong.baekjoonprofile.viewmodel.LoginViewModel

const val ANIMATION_DURATION = 300


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.loginComposable(
    loginViewModel: LoginViewModel,
    navigateToDetail: (Profile) -> Unit
) {
    this.composable(
        NavRoute.LOGIN,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(ANIMATION_DURATION)
            )
        }
    ) {
        LoginView(
            profileLoadingState = loginViewModel.profileLoadingState.collectAsState().value,
            navigateToDetail = navigateToDetail
        )
        {
            loginViewModel.getProfile(it)
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.infoComposable(
    navigateToLogin: () -> Unit
) {
    this.composable(
        NavRoute.INFO,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(ANIMATION_DURATION)
            )
        }, arguments = listOf(navArgument("profile") { type = ProfileType() })
    ) {

        val profile = it.arguments?.getSerializable("profile") as Profile
        DetailInfoView(
            profile = profile,
            navigateToLogin = navigateToLogin
        )
    }

}