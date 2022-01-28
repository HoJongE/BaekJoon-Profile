package per.hojong.baekjoonprofile.data

import per.hojong.baekjoonprofile.data.source.ProfileRemoteSource
import per.hojong.baekjoonprofile.model.Profile
import javax.inject.Singleton

@Singleton
class ProfileRepository(private val remoteSource: ProfileRemoteSource) {

    /**
     * load Profile with given ID String
     * @param id ID
     * @return Profile Data
     */
    suspend fun loadProfile(id: String): Profile = remoteSource.getUserInfo(id)
}