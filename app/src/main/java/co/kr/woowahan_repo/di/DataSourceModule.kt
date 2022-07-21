package co.kr.woowahan_repo.di

import co.kr.woowahan_repo.data.datasource.WoowahanSharedPreferences
import co.kr.woowahan_repo.domain.datasource.GithubTokenDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindGithubTokenDataSource(impl: WoowahanSharedPreferences): GithubTokenDataSource

}