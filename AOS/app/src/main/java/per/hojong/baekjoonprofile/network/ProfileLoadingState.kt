package per.hojong.baekjoonprofile.network

import per.hojong.baekjoonprofile.model.Profile

/**
 * Class that handle Profile Loading UI State
 */
sealed class ProfileLoadingState {
    object Loading : ProfileLoadingState()
    object Empty : ProfileLoadingState()
    data class Success(val profile: Profile) : ProfileLoadingState()
    data class Error(val message: String?) : ProfileLoadingState()
}
