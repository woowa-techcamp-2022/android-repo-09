package co.kr.woowahan_repo.domain.repository

import androidx.paging.PagingData
import co.kr.woowahan_repo.domain.model.GithubRepositorySearchModel
import kotlinx.coroutines.flow.Flow

interface GithubRepositorySearchRepository {
    suspend fun searchQuery(query: String, page: Int): Result<List<GithubRepositorySearchModel>>
    suspend fun fetchSearchLimit(): Result<Int>
    fun searchQueryPaging(query: String): Flow<PagingData<GithubRepositorySearchModel>>
}