package co.kr.woowahan_repo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.kr.woowahan_repo.di.ServiceLocator
import co.kr.woowahan_repo.domain.SearchRepositoryPagingSource
import co.kr.woowahan_repo.domain.model.GithubRepositorySearchModel
import co.kr.woowahan_repo.domain.repository.GithubRepositorySearchRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class GithubRepositorySearchRepositoryImpl: GithubRepositorySearchRepository {
    private val repositorySearchService = ServiceLocator.getRepositorySearchService()
    private val githubSearchLimitSearchRepository = ServiceLocator.getGithubSearchLimitService()

    override suspend fun searchQuery(query: String, page: Int): Result<List<GithubRepositorySearchModel>> {
        return kotlin.runCatching {
            repositorySearchService.searchQuery(
                query, page
            ).items.map {
//                Timber.tag("data search model").d(
//                    "search debug [name: ${it.name}, " +
//                            "description: ${it.description}," +
//                            "user: ${it.owner.login}," +
//                            "profile_url: ${it.owner.avatarUrl}," +
//                            "language: ${it.language}, " +
//                            "start: ${it.stargazersCount}"
//                )
                it.toEntity()
            }
        }
    }

    override suspend fun fetchSearchLimit(): Result<Int> {
        return kotlin.runCatching {
            githubSearchLimitSearchRepository.fetchSearchLimitInfo().getSearchLimit()
        }
    }

    override fun searchQueryPaging(query: String): Flow<PagingData<GithubRepositorySearchModel>> {
        Timber.d("paging debug $query")
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = {
                SearchRepositoryPagingSource(
                    repositorySearchService,
                    query
                )
            }
        ).flow
    }

}