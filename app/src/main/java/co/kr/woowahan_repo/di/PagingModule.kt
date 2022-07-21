package co.kr.woowahan_repo.di

import androidx.paging.PagingSource
import co.kr.woowahan_repo.data.datasource.NotificationsPagingSource
import co.kr.woowahan_repo.data.repository.NotificationPagingRepositoryImpl
import co.kr.woowahan_repo.data.service.GithubNotificationsService
import co.kr.woowahan_repo.domain.model.GithubNotification
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagingModule {
    @Provides
    @Singleton
    fun provideNotificationPagingSource(githubNotificationsService: GithubNotificationsService): PagingSource<Int, GithubNotification> =
        NotificationsPagingSource(githubNotificationsService)

    @Provides
    @Singleton
    fun provideNotificationPagingRepository(notificationsPagingSource: NotificationsPagingSource) =
        NotificationPagingRepositoryImpl(notificationsPagingSource)
}