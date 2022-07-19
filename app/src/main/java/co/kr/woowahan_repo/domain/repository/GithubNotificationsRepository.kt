package co.kr.woowahan_repo.domain.repository

import co.kr.woowahan_repo.data.model.response.GithubNotificationAsReadResponse
import co.kr.woowahan_repo.domain.model.GithubNotification
import retrofit2.Response

interface GithubNotificationsRepository {
    suspend fun fetchNotifications(page: Int): Result<List<GithubNotification>>
    suspend fun patchNotificationAsRead(threadId: String): Result<Response<GithubNotificationAsReadResponse>>
}