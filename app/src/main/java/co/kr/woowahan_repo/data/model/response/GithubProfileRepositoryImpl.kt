package co.kr.woowahan_repo.data.model.response

import co.kr.woowahan_repo.data.repository.GithubProfileRepository
import co.kr.woowahan_repo.data.service.GithubProfileService
import co.kr.woowahan_repo.domain.model.GithubProfileModel
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