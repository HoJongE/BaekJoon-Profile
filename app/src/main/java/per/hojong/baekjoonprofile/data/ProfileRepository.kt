package per.hojong.baekjoonprofile.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import per.hojong.baekjoonprofile.data.source.ProfileRemoteSource
import per.hojong.baekjoonprofile.model.Profile
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class ProfileRepository(
    private val remoteSource: ProfileRemoteSource,
    private val coroutineContext: CoroutineDispatcher
) {

    /**
     * load Profile with given ID String
     * @param id ID
     * @return Profile Data
     */
    suspend fun loadProfile(id: String): Profile =
        withContext(coroutineContext) {
            remoteSource.getUserInfo(id)
        }
}