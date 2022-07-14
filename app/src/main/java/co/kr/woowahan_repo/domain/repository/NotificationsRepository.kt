package co.kr.woowahan_repo.domain.repository

import co.kr.woowahan_repo.domain.entity.Notification

interface NotificationsRepository {
    suspend fun fetchNotifications(page: Int): List<Notification>
}