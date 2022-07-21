package co.kr.woowahan_repo.data.repository

import co.kr.woowahan_repo.data.service.GithubCommentsService
import co.kr.woowahan_repo.data.service.GithubNotificationsService
import co.kr.woowahan_repo.domain.model.GithubNotification
import co.kr.woowahan_repo.domain.repository.GithubNotificationsRepository
import javax.inject.Inject

class GithubNotificationsRepositoryImpl @Inject constructor(
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

    override suspend fun patchNotificationAsRead(threadId: String): Boolean {
        val result = runCatching {
            notificationsService.patchNotificationAsRead(threadId)
        }
        return result.isSuccess
    }
}