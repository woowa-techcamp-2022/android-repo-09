package co.kr.woowahan_repo.data.api

import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.data.service.GithubOAuthAccessTokenService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private val client = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.GITHUB_OAUTH_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val githubOAuthAccessTokenService = retrofit.create(GithubOAuthAccessTokenService::class.java)

    fun <T>createService(service: Class<T>): T {
        return retrofit.create(service)
    }
}