package co.kr.woowahan_repo.data.service

import co.kr.woowahan_repo.data.model.response.GithubUsersRepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface GithubUsersRepositoriesService {
    @GET
    suspend fun fetchUsersRepositories(
        @Url url: String
    ): List<GithubUsersRepositoryResponse>
}