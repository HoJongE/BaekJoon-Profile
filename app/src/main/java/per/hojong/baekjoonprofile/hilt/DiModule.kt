package per.hojong.baekjoonprofile.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import per.hojong.baekjoonprofile.data.ProfileRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiModule {
    @Singleton
    @Provides
    fun provideProfileRepository() = ProfileRepository()
}