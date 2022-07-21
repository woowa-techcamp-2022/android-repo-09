package co.kr.woowahan_repo.di

import android.app.Application
import co.kr.woowahan_repo.domain.GithubApiDateFormat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *
 * AppModule
 * RetrofitModule
 * DataSourceModule
 * ServiceModule
 * RepositoryModule
 * 순으로 의존성이 주입되는 흐름
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @ApplicationContext
    @Provides
    @Singleton
    fun provideApplicationContext(application: Application) = application

    @Provides
    @Singleton
    fun provideGithubDateFormat(): GithubApiDateFormat = GithubApiDateFormat()


}