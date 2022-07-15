package co.kr.woowahan_repo.application

import co.kr.woowahan_repo.di.ServiceLocator
import co.kr.woowahan_repo.data.api.ServiceCreator
import co.kr.woowahan_repo.data.service.GithubIssuesService
import co.kr.woowahan_repo.data.model.repositoryimpl.GithubIssuesRepositoryImpl

class AppContainer {

    val issuesRepository: GithubIssuesRepositoryImpl by lazy {
        GithubIssuesRepositoryImpl(
            ServiceLocator.getGithubIssuesService()
        )
    }
}