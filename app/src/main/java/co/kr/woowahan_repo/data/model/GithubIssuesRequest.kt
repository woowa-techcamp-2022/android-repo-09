package co.kr.woowahan_repo.data.model

import retrofit2.http.Path

data class GithubIssuesRequest(
    @Path("state")val state: String,
    @Path("page")val page: Int = 1, // default is 1
    @Path("per_page")val pageItemCount: Int = 100 // max is 100, default is 30
)