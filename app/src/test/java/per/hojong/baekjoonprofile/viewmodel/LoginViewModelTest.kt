package per.hojong.baekjoonprofile.viewmodel

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import per.hojong.baekjoonprofile.data.ProfileRepository
import per.hojong.baekjoonprofile.network.ProfileLoadingState

class LoginViewModelTest {
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUpLoginViewModel() {
        loginViewModel = LoginViewModel(ProfileRepository())
    }

    @Test
    fun getInitialProfileState() {
        val profileState = loginViewModel.profileLoadingState.value

        assertEquals(profileState, ProfileLoadingState.Empty)
    }

}