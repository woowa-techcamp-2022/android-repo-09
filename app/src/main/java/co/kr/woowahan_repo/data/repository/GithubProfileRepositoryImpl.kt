package co.kr.woowahan_repo.data.repository

import co.kr.woowahan_repo.data.service.GithubProfileService
import co.kr.woowahan_repo.data.service.GithubUsersRepositoriesService
import co.kr.woowahan_repo.domain.model.GithubProfileModel
import co.kr.woowahan_repo.domain.repository.GithubProfileRepository
import timber.log.Timber
import javax.inject.Inject

class GithubProfileRepositoryImpl @Inject constructor(
    private val githubProfileService: GithubProfileService,
    private val githubUsersRepositoriesService: GithubUsersRepositoriesService
): GithubProfileRepository {
    private var cacheProfileUrl: String? = null

    override suspend fun fetchProfileUrl(): Result<String> {
        Timber.d("fetchProfileUrl cache[$cacheProfileUrl]")
        return if(cacheProfileUrl.isNullOrBlank()){
            kotlin.runCatching {
                githubProfileService.fetchGithubProfile().toProfileUrl()
            }.onSuccess {
                cacheProfileUrl = it
            }
        }else
            Result.success(cacheProfileUrl!!)
    }

    override suspend fun fetchProfile(): Result<GithubProfileModel> {
        return kotlin.runCatching {
            val res = githubProfileService.fetchGithubProfile()
            res.toEntity().apply {
                starCount = githubUsersRepositoriesService.fetchUsersRepositories(
                    res.reposUrl
                ).sumOf { it.stargazersCount }
            }
        }
    }
}