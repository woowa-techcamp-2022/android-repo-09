package co.kr.woowahan_repo.data.service

import co.kr.woowahan_repo.data.model.response.GithubCommentsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubCommentsService {
    @GET("{url}")
    suspend fun fetchCommentsCount(
        @Path(value = "url", encoded = true) url: String
    ): GithubCommentsResponse
}