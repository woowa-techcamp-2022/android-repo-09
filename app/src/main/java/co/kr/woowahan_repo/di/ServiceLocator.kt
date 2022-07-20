package co.kr.woowahan_repo.di

import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.data.api.interceptor.AuthInterceptor
import co.kr.woowahan_repo.data.repository.*
import co.kr.woowahan_repo.data.service.*
import co.kr.woowahan_repo.domain.repository.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    var accessToken: String = ""

    private fun getAuthInterceptor() = AuthInterceptor(accessToken)

    private fun getOAuthClient() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    private fun getApiClient() = OkHttpClient.Builder()
        .addInterceptor(getAuthInterceptor())
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    private fun getOAuthRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.GITHUB_OAUTH_BASE_URL)
        .client(getOAuthClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun getApiRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.GITHUB_API_BASE_URL)
        .client(getApiClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * service
     */
    private fun provideOAuthAccessTokenService(): GithubOAuthAccessTokenService =
        getOAuthRetrofit().create(GithubOAuthAccessTokenService::class.java)

    private fun provideGithubIssuesService(): GithubIssuesService =
        getApiRetrofit().create(GithubIssuesService::class.java)

    private fun provideNotificationsService(): GithubNotificationsService =
        getApiRetrofit().create(GithubNotificationsService::class.java)

    private fun provideGithubProfileService(): GithubProfileService =
        getApiRetrofit().create(GithubProfileService::class.java)

    private fun provideRepositorySearchService(): GithubRepositorySearchService =
        getApiRetrofit().create(GithubRepositorySearchService::class.java)

    private fun provideGithubSearchLimitService(): GithubSearchLimitService =
        getApiRetrofit().create(GithubSearchLimitService::class.java)

    private fun provideGithubUsersRepositoriesService(): GithubUsersRepositoriesService =
        getApiRetrofit().create(GithubUsersRepositoriesService::class.java)

    private fun provideGithubCommentsService(): GithubCommentsService =
        getApiRetrofit().create(GithubCommentsService::class.java)


    /**
     * repository
     */
    fun provideGithubOAuthRepository(): GithubOAuthRepository =
        GithubOAuthRepositoryImpl(provideOAuthAccessTokenService())

    fun provideGithubIssuesRepository(): GithubIssuesRepository =
        GithubIssuesRepositoryImpl(provideGithubIssuesService())

    fun provideNotificationsRepository(): GithubNotificationsRepository =
        GithubNotificationsRepositoryImpl(
            provideNotificationsService(),
            provideGithubCommentsService()
        )

    fun provideGithubSearchRepository(): GithubRepositorySearchRepository =
        GithubRepositorySearchRepositoryImpl(
            provideRepositorySearchService(),
            provideGithubSearchLimitService()
        )

    fun provideGithubProfileRepository(): GithubProfileRepository =
        GithubProfileRepositoryImpl(
            provideGithubProfileService(),
            provideGithubUsersRepositoriesService()
        )

}