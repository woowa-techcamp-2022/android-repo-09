package co.kr.woowahan_repo.data.repository

import co.kr.woowahan_repo.data.service.GithubProfileService
import co.kr.woowahan_repo.domain.model.GithubProfileModel
import co.kr.woowahan_repo.domain.repository.GithubProfileRepository
import timber.log.Timber

class GithubProfileRepositoryImpl(
    private val githubProfileService: GithubProfileService
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
            githubProfileService.fetchGithubProfile().toEntity()
        }
    }
}