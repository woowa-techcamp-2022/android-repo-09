package co.kr.woowahan_repo.domain.repository

import co.kr.woowahan_repo.domain.model.GithubNotification

interface NotificationsRepository {
    suspend fun fetchNotifications(page: Int): Result<List<GithubNotification>>
}