package co.kr.woowahan_repo.di

import co.kr.woowahan_repo.data.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ServiceModule {

    @Provides
    fun provideOAuthAccessTokenService(@OAuthRetrofit retrofit: Retrofit): GithubOAuthAccessTokenService =
        retrofit.create(GithubOAuthAccessTokenService::class.java)

    @Provides
    fun provideGithubIssuesService(@ApiRetrofit retrofit: Retrofit): GithubIssuesService =
        retrofit.create(GithubIssuesService::class.java)

    @Provides
    fun provideNotificationsService(@ApiRetrofit retrofit: Retrofit): GithubNotificationsService =
        retrofit.create(GithubNotificationsService::class.java)

    @Provides
    fun provideGithubProfileService(@ApiRetrofit retrofit: Retrofit): GithubProfileService =
        retrofit.create(GithubProfileService::class.java)

    @Provides
    fun provideRepositorySearchService(@ApiRetrofit retrofit: Retrofit): GithubRepositorySearchService =
        retrofit.create(GithubRepositorySearchService::class.java)

    @Provides
    fun provideGithubSearchLimitService(@ApiRetrofit retrofit: Retrofit): GithubSearchLimitService =
        retrofit.create(GithubSearchLimitService::class.java)

    @Provides
    fun provideGithubUsersRepositoriesService(@ApiRetrofit retrofit: Retrofit): GithubUsersRepositoriesService =
        retrofit.create(GithubUsersRepositoriesService::class.java)

    @Provides
    fun provideGithubCommentsService(@ApiRetrofit retrofit: Retrofit): GithubCommentsService =
        retrofit.create(GithubCommentsService::class.java)

}