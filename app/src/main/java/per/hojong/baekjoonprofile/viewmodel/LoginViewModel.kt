package per.hojong.baekjoonprofile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import per.hojong.baekjoonprofile.data.ProfileRepository
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.network.ProfileLoadingState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {
    private val _profileLoadingState =
        MutableStateFlow<ProfileLoadingState>(ProfileLoadingState.Empty)

    val profileLoadingState = _profileLoadingState.asStateFlow()


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }
    private val ioDispatchers = Dispatchers.IO + exceptionHandler
    private val uiDispatchers = Dispatchers.Main + exceptionHandler

    /**
     * get Profile with given String ID
     * if user give success callback, invoke it
     * @param id Profile ID
     * @param onSuccess Success Callback
     */
    fun getProfile(id: String, onSuccess: ((Profile) -> Unit)? = null) {
        viewModelScope.launch(ioDispatchers) {
            _profileLoadingState.value = ProfileLoadingState.Loading
            val profile = repository.loadProfile(id)
            _profileLoadingState.value = ProfileLoadingState.Success(profile)
            onSuccess?.invoke(profile)
        }
    }

    fun logout() {
        _profileLoadingState.value = ProfileLoadingState.Empty
    }

    /**
     * Coroutine Error catch callback Function
     * @param error error
     */
    private fun onError(error: Throwable) {
        _profileLoadingState.value = ProfileLoadingState.Error(error.localizedMessage)
    }
}