package co.kr.woowahan_repo.di

import co.kr.woowahan_repo.data.datasource.GithubProfileDataSourceImpl
import co.kr.woowahan_repo.data.datasource.WoowahanSharedPreferences
import co.kr.woowahan_repo.domain.datasource.GithubProfileDataSource
import co.kr.woowahan_repo.domain.datasource.GithubTokenDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindGithubTokenDataSource(impl: WoowahanSharedPreferences): GithubTokenDataSource

    @Binds
    @Singleton
    abstract fun bindGithubProfileDataSource(impl: GithubProfileDataSourceImpl): GithubProfileDataSource

}