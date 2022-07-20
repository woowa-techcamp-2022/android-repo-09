package co.kr.woowahan_repo.di

import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.data.api.interceptor.AuthInterceptor
import co.kr.woowahan_repo.data.repository.GithubIssuesRepositoryImpl
import co.kr.woowahan_repo.data.service.*
import co.kr.woowahan_repo.domain.repository.GithubIssuesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

/**
 * provide 는 lazy 와 비슷하게, 해당 객체가 사용될때 호출된다
 * 때문에 service 는 singleTone 으로 설정
 *
 * Hilt 에 의해 주입받은 변수 객체는 private 할 수 없다는데, view model 의 repository 들은 모두 private 인데 문제없이 실행되고 있다
 *
 * Retrofit Module 설계?
 *
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
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

    private fun getApiRetrofit(): Retrofit {
        Timber.d("hilt debug getApiRetrofit")
        return Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_API_BASE_URL)
            .client(getApiClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOAuthAccessTokenService(): GithubOAuthAccessTokenService =
        getOAuthRetrofit().create(GithubOAuthAccessTokenService::class.java)

    @Provides
    @Singleton
    fun provideGithubIssuesService(): GithubIssuesService =
        getApiRetrofit().create(GithubIssuesService::class.java)

    @Provides
    @Singleton
    fun provideNotificationsService(): GithubNotificationsService =
        getApiRetrofit().create(GithubNotificationsService::class.java)

    @Provides
    @Singleton
    fun provideGithubProfileService(): GithubProfileService =
        getApiRetrofit().create(GithubProfileService::class.java)

    @Provides
    @Singleton
    fun provideRepositorySearchService(): GithubRepositorySearchService =
        getApiRetrofit().create(GithubRepositorySearchService::class.java)

    @Provides
    @Singleton
    fun provideGithubSearchLimitService(): GithubSearchLimitService =
        getApiRetrofit().create(GithubSearchLimitService::class.java)

    @Provides
    @Singleton
    fun provideGithubUsersRepositoriesService(): GithubUsersRepositoriesService =
        getApiRetrofit().create(GithubUsersRepositoriesService::class.java)

    @Provides
    @Singleton
    fun provideGithubCommentsService(): GithubCommentsService =
        getApiRetrofit().create(GithubCommentsService::class.java)

}