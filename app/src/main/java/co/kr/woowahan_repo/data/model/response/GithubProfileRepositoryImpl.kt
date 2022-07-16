package co.kr.woowahan_repo.data.model.response

import co.kr.woowahan_repo.data.repository.GithubProfileRepository
import co.kr.woowahan_repo.data.service.GithubProfileService
import co.kr.woowahan_repo.domain.model.GithubProfileModel

class GithubProfileRepositoryImpl(
    private val githubProfileService: GithubProfileService
): GithubProfileRepository {

    override suspend fun fetchProfileUrl(): Result<String> {
        return kotlin.runCatching {
            githubProfileService.fetchGithubProfile().toProfileUrl()
        }
    }

    override suspend fun fetchProfile(): Result<GithubProfileModel> {
        return kotlin.runCatching {
            githubProfileService.fetchGithubProfile().toEntity()
        }
    }
}