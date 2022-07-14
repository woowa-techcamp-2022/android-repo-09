package co.kr.woowahan_repo.data.repository

import co.kr.woowahan_repo.data.service.NotificationsService
import co.kr.woowahan_repo.domain.entity.Notification
import co.kr.woowahan_repo.domain.repository.NotificationsRepository

class NotificationsRepositoryImpl(
    private val notificationsService: NotificationsService
) : NotificationsRepository {
    override suspend fun fetchNotifications(page: Int): List<Notification> {
        return notificationsService.fetchNotifications(page, 50)
            .map { it.toEntity() }
    }
}