package co.kr.woowahan_repo.data.repository

import co.kr.woowahan_repo.data.service.GithubCommentsService
import co.kr.woowahan_repo.di.ServiceLocator
import co.kr.woowahan_repo.domain.model.GithubComments
import co.kr.woowahan_repo.domain.repository.GithubCommentsRepository

class GithubCommentsRepositoryImpl(
    private val githubCommentsService: GithubCommentsService = ServiceLocator.getGithubCommentsService()
) : GithubCommentsRepository {
    override suspend fun fetchComments(url: String): Result<GithubComments> {
        return runCatching {
            githubCommentsService.fetchCommentsCount(url).toEntity()
        }
    }
}