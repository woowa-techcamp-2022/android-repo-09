package co.kr.woowahan_repo.data.repository

import co.kr.woowahan_repo.data.model.response.GithubNotificationAsReadResponse
import co.kr.woowahan_repo.data.service.GithubCommentsService
import co.kr.woowahan_repo.data.service.GithubNotificationsService
import co.kr.woowahan_repo.domain.model.GithubNotification
import co.kr.woowahan_repo.domain.repository.GithubNotificationsRepository
import retrofit2.Response

class GithubNotificationsRepositoryImpl(
    private val notificationsService: GithubNotificationsService,
    private val commentsService: GithubCommentsService
) : GithubNotificationsRepository {
    override suspend fun fetchNotifications(page: Int): Result<List<GithubNotification>> {
        return runCatching {
            notificationsService.fetchNotifications(page, 10).map {
                it.toEntity().apply {
                    comments =
                        if (reason == "review_requested") {
                            commentsService.fetchCommentsCount(url).reviewComments
                        } else {
                            commentsService.fetchCommentsCount(url).comments
                        }
                }
            }
        }
    }

    override suspend fun patchNotificationAsRead(threadId: String): Result<Response<GithubNotificationAsReadResponse>> {
        return runCatching {
            notificationsService.patchNotificationAsRead(threadId)
        }
    }
}