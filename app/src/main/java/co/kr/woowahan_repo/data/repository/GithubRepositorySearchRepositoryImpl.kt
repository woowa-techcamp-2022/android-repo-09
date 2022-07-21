package co.kr.woowahan_repo.data.repository

import co.kr.woowahan_repo.data.service.GithubRepositorySearchService
import co.kr.woowahan_repo.data.service.GithubSearchLimitService
import co.kr.woowahan_repo.domain.model.GithubRepositorySearchModel
import co.kr.woowahan_repo.domain.repository.GithubRepositorySearchRepository
import timber.log.Timber

class GithubRepositorySearchRepositoryImpl(
    private val repositorySearchService: GithubRepositorySearchService,
    private val githubSearchLimitSearchRepository: GithubSearchLimitService
): GithubRepositorySearchRepository {


    override suspend fun searchQuery(query: String, page: Int): Result<List<GithubRepositorySearchModel>> {
        return kotlin.runCatching {
            repositorySearchService.searchQuery(
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

    override suspend fun fetchSearchLimit(): Result<Int> {
        return kotlin.runCatching {
            githubSearchLimitSearchRepository.fetchSearchLimitInfo().getSearchLimit()
        }
    }
}