package co.kr.woowahan_repo.di

import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.data.api.interceptor.AuthInterceptor
import co.kr.woowahan_repo.data.model.repositoryimpl.GithubRepositorySearchRepositoryImpl
import co.kr.woowahan_repo.data.repository.GithubIssuesRepositoryImpl
import co.kr.woowahan_repo.domain.repository.GithubRepositorySearchRepository
import co.kr.woowahan_repo.data.repository.GithubOAuthRepositoryImpl
import co.kr.woowahan_repo.data.repository.GithubProfileRepositoryImpl
import co.kr.woowahan_repo.domain.repository.GithubProfileRepository
import co.kr.woowahan_repo.data.repository.NotificationsRepositoryImpl
import co.kr.woowahan_repo.data.service.*
import co.kr.woowahan_repo.domain.repository.GithubIssuesRepository
import co.kr.woowahan_repo.domain.repository.GithubOAuthRepository
import co.kr.woowahan_repo.domain.repository.NotificationsRepository
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

    fun getGithubOAuthRepository(): GithubOAuthRepository = GithubOAuthRepositoryImpl()
    fun getOAuthAccessTokenService(): GithubOAuthAccessTokenService =
        getOAuthRetrofit().create(GithubOAuthAccessTokenService::class.java)

    private fun getNotificationsService(): NotificationsService =
        getApiRetrofit().create(NotificationsService::class.java)
    fun getNotificationsRepository(): NotificationsRepository =
        NotificationsRepositoryImpl(getNotificationsService())

    fun getRepositorySearchService(): GithubRepositorySearchService =
        getApiRetrofit().create(GithubRepositorySearchService::class.java)
    fun getGithubSearchRepository(): GithubRepositorySearchRepository =
        GithubRepositorySearchRepositoryImpl()

    private fun getGithubIssuesService(): GithubIssuesService =
        getApiRetrofit().create(GithubIssuesService::class.java)
    fun getGithubIssuesRepository(): GithubIssuesRepository =
        GithubIssuesRepositoryImpl(getGithubIssuesService())

    private fun getGithubProfileService(): GithubProfileService =
        getApiRetrofit().create(GithubProfileService::class.java)
    fun getGithubProfileRepository(): GithubProfileRepository =
        GithubProfileRepositoryImpl(getGithubProfileService())
}