package per.hojong.baekjoonprofile.data

import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.network.SolvedApiService
import javax.inject.Singleton

@Singleton
class ProfileRepository {

    private val remoteSource = SolvedApiService.getInstance()

    /**
     * load Profile with given ID String
     * @param id ID
     * @return Profile Data
     */
    suspend fun loadProfile(id: String): Profile = remoteSource.getUserInfo(id)
}