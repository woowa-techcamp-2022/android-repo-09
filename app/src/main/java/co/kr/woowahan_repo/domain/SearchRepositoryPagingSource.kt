package co.kr.woowahan_repo.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.kr.woowahan_repo.data.service.GithubRepositorySearchService
import co.kr.woowahan_repo.domain.model.GithubRepositorySearchModel
import timber.log.Timber

/**
 * https://developer.android.com/codelabs/android-paging?hl=ko#4
 * 기본적으로 초기 로드 크기는 3 * 페이지 크기입니다. 그런 상태로 Paging을 사용하면 목록이 처음 로드될 때 사용자에게 충분한 항목이 표시되며 사용자가 로드된 항목을 지나 스크롤하지 않으면 너무 많은 네트워크 요청이 트리거되지 않습니다.
 * PagingSource 구현에서 다음 키를 계산할 때 이 내용을 고려해야 합니다.
 */
class SearchRepositoryPagingSource(
    private val searchRepositorySearchService: GithubRepositorySearchService,
    private val query: String,
    private val defaultPage: Int = 1
): PagingSource<Int, GithubRepositorySearchModel>() {

    // Try to find the page key of the closest page to anchorPosition, from
    // either the prevKey or the nextKey, but you need to handle nullability
    // here:
    //  * prevKey == null -> anchorPage is the first page.
    //  * nextKey == null -> anchorPage is the last page.
    //  * both prevKey and nextKey null -> anchorPage is the initial page, so
    //    just return null.
    override fun getRefreshKey(state: PagingState<Int, GithubRepositorySearchModel>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            // 현재 key 값을 리턴
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepositorySearchModel> {
        Timber.d("paging debug load $query")
        val position = params.key ?: defaultPage
        kotlin.runCatching {
            searchRepositorySearchService.searchQuery(
                query, position, params.loadSize // 첫 실행시 key 가 null
            )
        }.onSuccess {
            val nextKey = if(it.items.isEmpty()){ //  상응하는 방향으로 목록을 로드할 수 없는 경우 -> list 가 비어서 도착했다면, 마지막 페이지라는 뜻
                null
            }else{
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
//                position + (params.loadSize / NETWORK_PAGE_SIZE)
                position.plus(1) // 다음 페이지 전달
            }
            // 검색 결과가 없는 query 를 날리면 page 가 증가하면서 계속 호출하는 현상을 발견 -> prev key 수정
            val prevKey = if(it.items.isEmpty() || params.key == defaultPage)
                null
            else
                position.minus(1)

            Timber.d("paging debug nextKey[$nextKey]")
            return LoadResult.Page(
                it.items.map { it.toEntity() },
                prevKey,
                nextKey
            )
        }.onFailure {
            return LoadResult.Error(it)
        }

        return LoadResult.Error(Throwable("???"))
    }
}