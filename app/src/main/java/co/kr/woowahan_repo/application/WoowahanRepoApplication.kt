package co.kr.woowahan_repo.application

import android.app.Application
import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.di.ServiceLocator
import co.kr.woowahan_repo.domain.repository.*
import timber.log.Timber

class WoowahanRepoApplication: Application() {

    val oAuthRepository: GithubOAuthRepository get() = ServiceLocator.provideGithubOAuthRepository()
    val issueRepository: GithubIssuesRepository get() = ServiceLocator.provideGithubIssuesRepository()
    val notificationRepository: GithubNotificationsRepository get() = ServiceLocator.provideNotificationsRepository()
    val githubRepositorySearchRepository: GithubRepositorySearchRepository get() = ServiceLocator.provideGithubSearchRepository()
    val githubProfileRepository: GithubProfileRepository get() = ServiceLocator.provideGithubProfileRepository()

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}