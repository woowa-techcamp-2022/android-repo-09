package co.kr.woowahan_repo.di

import android.app.Application
import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.data.api.interceptor.AuthInterceptor
import co.kr.woowahan_repo.data.service.*
import co.kr.woowahan_repo.domain.GithubTokenDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Qualifier
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
    @ApplicationContext
    @Provides
    @Singleton
    fun provideApplicationContext(application: Application) = application

//    private fun getAuthInterceptor() = AuthInterceptor()

    @Provides
    @Singleton
    fun provideAuthInterceptor(githubTokenDataSource: GithubTokenDataSource): AuthInterceptor = AuthInterceptor(githubTokenDataSource)

//    private fun getOAuthClient() = OkHttpClient.Builder()
//        .addInterceptor(
//            HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            }
//        )
//        .build()

    @OAuthClient
    @Provides
    @Singleton
    fun provideOAuthClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    @ApiClient
    @Provides
    @Singleton
    fun provideApiClient(authInterceptor: AuthInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

//    private fun getApiClient() = OkHttpClient.Builder()
//        .addInterceptor(getAuthInterceptor())
//        .addInterceptor(
//            HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            }
//        )
//        .build()

//    private fun getOAuthRetrofit(): Retrofit = Retrofit.Builder()
//        .baseUrl(BuildConfig.GITHUB_OAUTH_BASE_URL)
//        .client(getOAuthClient())
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    private fun getApiRetrofit(): Retrofit {
//        Timber.d("hilt debug getApiRetrofit")
//        return Retrofit.Builder()
//            .baseUrl(BuildConfig.GITHUB_API_BASE_URL)
//            .client(getApiClient())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }

    @OAuthRetrofit
    @Provides
    @Singleton
    fun provideOAuthRetrofit(@OAuthClient oAuthClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_OAUTH_BASE_URL)
            .client(oAuthClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @ApiRetrofit
    @Provides
    @Singleton
    fun provideApiRetrofit(@ApiClient apiClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_API_BASE_URL)
            .client(apiClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideOAuthAccessTokenService(@OAuthRetrofit retrofit: Retrofit): GithubOAuthAccessTokenService =
        retrofit.create(GithubOAuthAccessTokenService::class.java)

    @Provides
    @Singleton
    fun provideGithubIssuesService(@ApiRetrofit retrofit: Retrofit): GithubIssuesService =
        retrofit.create(GithubIssuesService::class.java)

    @Provides
    @Singleton
    fun provideNotificationsService(@ApiRetrofit retrofit: Retrofit): GithubNotificationsService =
        retrofit.create(GithubNotificationsService::class.java)

    @Provides
    @Singleton
    fun provideGithubProfileService(@ApiRetrofit retrofit: Retrofit): GithubProfileService =
        retrofit.create(GithubProfileService::class.java)

    @Provides
    @Singleton
    fun provideRepositorySearchService(@ApiRetrofit retrofit: Retrofit): GithubRepositorySearchService =
        retrofit.create(GithubRepositorySearchService::class.java)

    @Provides
    @Singleton
    fun provideGithubSearchLimitService(@ApiRetrofit retrofit: Retrofit): GithubSearchLimitService =
        retrofit.create(GithubSearchLimitService::class.java)

    @Provides
    @Singleton
    fun provideGithubUsersRepositoriesService(@ApiRetrofit retrofit: Retrofit): GithubUsersRepositoriesService =
        retrofit.create(GithubUsersRepositoriesService::class.java)

    @Provides
    @Singleton
    fun provideGithubCommentsService(@ApiRetrofit retrofit: Retrofit): GithubCommentsService =
        retrofit.create(GithubCommentsService::class.java)

}

@Qualifier
annotation class OAuthClient

@Qualifier
annotation class ApiClient

@Qualifier
annotation class OAuthRetrofit

@Qualifier
annotation class ApiRetrofit