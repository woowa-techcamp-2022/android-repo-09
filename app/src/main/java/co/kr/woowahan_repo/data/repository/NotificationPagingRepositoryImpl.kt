package co.kr.woowahan_repo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.kr.woowahan_repo.data.datasource.NotificationsPagingSource
import co.kr.woowahan_repo.domain.model.GithubNotification
import co.kr.woowahan_repo.domain.repository.NotificationPagingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationPagingRepositoryImpl @Inject constructor(
    private val notificationsPagingSource: NotificationsPagingSource
) : NotificationPagingRepository {
    override fun fetchNotifications(): Flow<PagingData<GithubNotification>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { notificationsPagingSource }
        ).flow
    }
}