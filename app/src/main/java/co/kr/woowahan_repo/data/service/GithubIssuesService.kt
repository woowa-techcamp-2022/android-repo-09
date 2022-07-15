package co.kr.woowahan_repo.data.service

import co.kr.woowahan_repo.data.model.response.GithubIssueResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubIssuesService {
    @GET("issues")
    suspend fun fetchIssues(
        @Query("state")state: String,
        @Query("filter")filter: String = "all",
        @Query("page")page: Int = 1, // default is 1
        @Query("per_page")pageItemCount: Int = 100 // max is 100, default is 30
    ): List<GithubIssueResponse>
}