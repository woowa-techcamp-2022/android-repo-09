package co.kr.woowahan_repo.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.kr.woowahan_repo.data.service.GithubNotificationsService
import co.kr.woowahan_repo.domain.model.GithubNotification
import javax.inject.Inject

class NotificationsPagingSource @Inject constructor(
    private val githubNotificationsService: GithubNotificationsService
) : PagingSource<Int, GithubNotification>() {
    override fun getRefreshKey(state: PagingState<Int, GithubNotification>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubNotification> {
        val page = params.key ?: 1
        return try {
            val items = githubNotificationsService.fetchNotifications(page, params.loadSize)
                .map { it.toEntity() }
            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + (params.loadSize / 10)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}