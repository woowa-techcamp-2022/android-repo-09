package co.kr.woowahan_repo.data.service

import co.kr.woowahan_repo.data.model.response.GithubNotificationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationsService {
    @GET("notifications")
    suspend fun fetchNotifications(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<GithubNotificationResponse>
}