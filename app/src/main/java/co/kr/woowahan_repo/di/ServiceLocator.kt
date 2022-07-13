package co.kr.woowahan_repo.di

import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.data.api.interceptor.AuthInterceptor
import co.kr.woowahan_repo.data.service.GithubOAuthAccessTokenService
import co.kr.woowahan_repo.data.service.NotificationsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    var accessToken: String = ""

    fun getAuthInterceptor() = AuthInterceptor(accessToken)

    fun getOAuthClient() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    fun getApiClient() = OkHttpClient.Builder()
        .addInterceptor(getAuthInterceptor())
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    fun getOAuthRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.GITHUB_OAUTH_BASE_URL)
        .client(getOAuthClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.GITHUB_API_BASE_URL)
        .client(getApiClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getOAuthAccessTokenService(): GithubOAuthAccessTokenService =
        getOAuthRetrofit().create(GithubOAuthAccessTokenService::class.java)

    fun getNotificationsService(): NotificationsService =
        getApiRetrofit().create(NotificationsService::class.java)
}