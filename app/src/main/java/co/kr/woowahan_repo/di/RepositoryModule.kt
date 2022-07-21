package co.kr.woowahan_repo.di

import co.kr.woowahan_repo.data.datasource.WoowahanSharedPreferences
import co.kr.woowahan_repo.data.repository.*
import co.kr.woowahan_repo.domain.datasource.GithubTokenDataSource
import co.kr.woowahan_repo.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGithubTokenDataSource(impl: WoowahanSharedPreferences): GithubTokenDataSource

    @Binds
    abstract fun bindOAuthRepository(impl: GithubOAuthRepositoryImpl): GithubOAuthRepository

    @Binds
    abstract fun bindIssuesRepository(impl: GithubIssuesRepositoryImpl): GithubIssuesRepository

    @Binds
    abstract fun bindNotificationsRepository(impl: GithubNotificationsRepositoryImpl): GithubNotificationsRepository

    @Binds
    abstract fun bindGithubSearchRepository(impl: GithubRepositorySearchRepositoryImpl): GithubRepositorySearchRepository

    @Binds
    abstract fun bindGithubProfileRepository(impl: GithubProfileRepositoryImpl): GithubProfileRepository

}