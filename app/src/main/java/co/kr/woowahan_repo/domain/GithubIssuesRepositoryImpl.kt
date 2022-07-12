package co.kr.woowahan_repo.domain

import co.kr.woowahan_repo.data.model.GithubIssuesRepository
import co.kr.woowahan_repo.data.service.GithubIssuesService

class GithubIssuesRepositoryImpl(
    private val githubIssuesService: GithubIssuesService
): GithubIssuesRepository {

    override suspend fun fetchIssues(state: String, page: Int): List<GithubIssueModel> {
        return githubIssuesService.fetchIssues(state, page).toEntity()
    }
}