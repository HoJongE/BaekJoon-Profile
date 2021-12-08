package per.hojong.baekjoonprofile.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.network.SolvedApiService
import java.lang.Exception

class LoginViewModel : ViewModel() {
    val profileResponse: MutableState<Profile?> = mutableStateOf(null)
    var profileLoadingState: Boolean by mutableStateOf(false)
    var profileLoadingError: Boolean by mutableStateOf(false)
    fun getProfile(id: String) {
        viewModelScope.launch {
            profileLoadingState = true
            val apiService = SolvedApiService.getInstance()
            try {
                profileResponse.value = apiService.getUserInfo(id)
                profileLoadingError = false
            } catch (e: Exception) {
                profileLoadingError = true
                e.printStackTrace()
            } finally {
                Log.d("login", profileResponse.value?.toString() ?: "fail")
                profileLoadingState = false
            }
        }
    }
}