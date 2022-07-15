package co.kr.woowahan_repo.domain.repository

import co.kr.woowahan_repo.domain.model.GithubRepositorySearchModel

interface GithubRepositorySearchRepository {
    suspend fun searchQuery(query: String, page: Int): List<GithubRepositorySearchModel>
}