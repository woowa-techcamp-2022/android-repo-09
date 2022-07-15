package co.kr.woowahan_repo.domain.repository

import co.kr.woowahan_repo.domain.entity.GithubNotification

interface NotificationsRepository {
    suspend fun fetchNotifications(page: Int): List<GithubNotification>
}