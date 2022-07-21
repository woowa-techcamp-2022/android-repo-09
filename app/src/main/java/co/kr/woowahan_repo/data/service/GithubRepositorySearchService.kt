package co.kr.woowahan_repo.data.service

import co.kr.woowahan_repo.data.model.response.RepositorySearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepositorySearchService {
    @GET("search/repositories")
    suspend fun searchQuery(
        @Query("q") query: String,
        @Query("page")page: Int = 1, // default is 1
        @Query("per_page")pageItemCount: Int = 100 // max is 100, default is 30
    ): RepositorySearchResponse
}