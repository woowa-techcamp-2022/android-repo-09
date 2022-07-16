package co.kr.woowahan_repo.data.service

import co.kr.woowahan_repo.data.model.response.GithubProfileResponse
import retrofit2.http.GET

interface GithubProfileService {
    @GET("user")
    suspend fun fetchGithubProfile(): GithubProfileResponse
}