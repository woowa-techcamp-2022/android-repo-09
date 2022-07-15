package co.kr.woowahan_repo.application

import co.kr.woowahan_repo.di.ServiceLocator
import co.kr.woowahan_repo.data.repository.GithubIssuesRepositoryImpl

class AppContainer {

    val issuesRepository: GithubIssuesRepositoryImpl by lazy {
        GithubIssuesRepositoryImpl(
            ServiceLocator.getGithubIssuesService()
        )
    }
}