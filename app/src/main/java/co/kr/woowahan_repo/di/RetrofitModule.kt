package co.kr.woowahan_repo.di

import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.data.api.interceptor.AuthInterceptor
import co.kr.woowahan_repo.domain.datasource.GithubTokenDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class OAuthClient

@Qualifier
annotation class ApiClient

@Qualifier
annotation class OAuthRetrofit

@Qualifier
annotation class ApiRetrofit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(githubTokenDataSource: GithubTokenDataSource): AuthInterceptor = AuthInterceptor(githubTokenDataSource)

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

}