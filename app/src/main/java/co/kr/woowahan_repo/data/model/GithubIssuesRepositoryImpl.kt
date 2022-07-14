package co.kr.woowahan_repo.data.model

import co.kr.woowahan_repo.data.service.GithubIssuesService
import co.kr.woowahan_repo.domain.entity.GithubIssueModel
import co.kr.woowahan_repo.domain.repository.GithubIssuesRepository

class GithubIssuesRepositoryImpl(
    private val githubIssuesService: GithubIssuesService
): GithubIssuesRepository {

    override suspend fun fetchIssues(state: String, page: Int): List<GithubIssueModel> {
        return githubIssuesService.fetchIssues(state, page).toEntity()
    }
}