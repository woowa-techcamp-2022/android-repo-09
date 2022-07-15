package co.kr.woowahan_repo.data.model.repositoryimpl

import co.kr.woowahan_repo.di.ServiceLocator
import co.kr.woowahan_repo.domain.model.GithubRepositorySearchModel
import co.kr.woowahan_repo.domain.repository.GithubRepositorySearchRepository
import timber.log.Timber

class GithubRepositorySearchRepositoryImpl: GithubRepositorySearchRepository {
    private val repositorySearchService = ServiceLocator.getRepositorySearchService()

    override suspend fun searchQuery(query: String, page: Int): List<GithubRepositorySearchModel> {
        return repositorySearchService.searchQuery(
            query, page
        ).items.map {
            Timber.tag("data search model").d(
                "search debug [name: ${it.name}, " +
                        "description: ${it.description}," +
                        "user: ${it.owner.login}," +
                        "profile_url: ${it.owner.avatarUrl}," +
                        "language: ${it.language}, " +
                        "start: ${it.stargazersCount}"
            )
            it.toEntity()
        }
    }
}