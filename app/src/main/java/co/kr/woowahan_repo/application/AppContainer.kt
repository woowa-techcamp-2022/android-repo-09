package co.kr.woowahan_repo.application

import co.kr.woowahan_repo.data.model.GithubIssuesRepositoryImpl
import co.kr.woowahan_repo.di.ServiceLocator

class AppContainer {

    val issuesRepository: GithubIssuesRepositoryImpl by lazy {
        GithubIssuesRepositoryImpl(
            ServiceLocator.getGithubIssuesService()
        )
    }
}