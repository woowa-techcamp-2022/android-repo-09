package co.kr.woowahan_repo.data.service

import co.kr.woowahan_repo.data.model.response.GithubSearchLimitResponse
import retrofit2.http.GET

/**
 * 분당 최대 30개
 */
interface GithubSearchLimitService {
    @GET("rate_limit")
    suspend fun fetchSearchLimitInfo(): GithubSearchLimitResponse
}