package co.kr.woowahan_repo.domain.repository

import androidx.paging.PagingData
import co.kr.woowahan_repo.domain.model.GithubNotification
import kotlinx.coroutines.flow.Flow

interface NotificationPagingRepository {
    fun fetchNotifications(): Flow<PagingData<GithubNotification>>
}