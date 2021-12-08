package per.hojong.baekjoonprofile.network

import per.hojong.baekjoonprofile.constant.BASE_URL
import per.hojong.baekjoonprofile.constant.USER_SHOW
import per.hojong.baekjoonprofile.model.Profile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SolvedApiService {

    @GET(USER_SHOW)
    suspend fun getUserInfo(@Query("handle") id: String): Profile

    companion object {
        private var apiService: SolvedApiService? = null
        fun getInstance(): SolvedApiService {
            if (null == apiService) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(SolvedApiService::class.java)
            }
            return apiService!!
        }
    }
}