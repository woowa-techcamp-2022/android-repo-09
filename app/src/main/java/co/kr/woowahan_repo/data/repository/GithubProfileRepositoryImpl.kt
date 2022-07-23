package co.kr.woowahan_repo.data.repository

import co.kr.woowahan_repo.data.service.GithubProfileService
import co.kr.woowahan_repo.data.service.GithubUsersRepositoriesService
import co.kr.woowahan_repo.domain.datasource.GithubProfileDataSource
import co.kr.woowahan_repo.domain.model.GithubProfileModel
import co.kr.woowahan_repo.domain.repository.GithubProfileRepository
import timber.log.Timber

class GithubProfileRepositoryImpl(
    private val githubProfileService: GithubProfileService,
    private val githubUsersRepositoriesService: GithubUsersRepositoriesService,
    private val githubProfileDataSource: GithubProfileDataSource
): GithubProfileRepository {

    override suspend fun fetchProfileUrl(): Result<String> {
        Timber.d("git profile cache get[${githubProfileDataSource.fetchProfileUrl()}]")
        return if(githubProfileDataSource.fetchProfileUrl().isBlank()){
            kotlin.runCatching {
                githubProfileService.fetchGithubProfile().toProfileUrl()
            }.onSuccess {
                githubProfileDataSource.updateProfileUrl(it)
            }
        }else
            Result.success(githubProfileDataSource.fetchProfileUrl())
    }

    override suspend fun fetchProfile(): Result<GithubProfileModel> {
        return kotlin.runCatching {
            val res = githubProfileService.fetchGithubProfile()
            res.toEntity().apply {
                starCount = githubUsersRepositoriesService.fetchUsersRepositories(
                    res.reposUrl
                ).sumOf { it.stargazersCount }
            }.also {
                githubProfileDataSource.updateProfileUrl(it.profileImage)
            }
        }
    }
}