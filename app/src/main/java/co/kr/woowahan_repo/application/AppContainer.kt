package co.kr.woowahan_repo.application

import co.kr.woowahan_repo.data.api.ServiceCreator
import co.kr.woowahan_repo.data.service.GithubIssuesService
import co.kr.woowahan_repo.domain.GithubIssuesRepositoryImpl

class AppContainer {

    val issuesRepository: GithubIssuesRepositoryImpl by lazy {
        GithubIssuesRepositoryImpl(
            ServiceCreator.createService(GithubIssuesService::class.java)
        )
    }
}