package per.hojong.baekjoonprofile.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.network.SolvedApiService

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var profileResponse: Profile? by mutableStateOf(null)
    var loginSuccess: Boolean by mutableStateOf(false)
    var profileLoadingState: Boolean by mutableStateOf(false)
    var profileLoadingError: Boolean by mutableStateOf(false)

    fun getProfile(id: String,) {
        viewModelScope.launch {
            profileLoadingState = true
            val apiService = SolvedApiService.getInstance()
            try {
                profileResponse = apiService.getUserInfo(id)
                profileLoadingError = false
                loginSuccess = true
            } catch (e: Exception) {
                profileLoadingError = true
                loginSuccess = false
                e.printStackTrace()
            } finally {
                profileLoadingState = false
            }
        }
    }
}