package co.kr.woowahan_repo.data.service

import co.kr.woowahan_repo.data.model.response.GithubNotificationResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubNotificationsService {
    @GET("notifications")
    suspend fun fetchNotifications(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<GithubNotificationResponse>

    @PATCH("notifications/threads/{thread_id}")
    suspend fun patchNotificationAsRead(
        @Path("thread_id") threadId: String
    )
}