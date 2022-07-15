package co.kr.woowahan_repo.data.repository

import co.kr.woowahan_repo.data.service.GithubIssuesService
import co.kr.woowahan_repo.domain.model.GithubIssueModel
import co.kr.woowahan_repo.domain.repository.GithubIssuesRepository

class GithubIssuesRepositoryImpl(
    private val githubIssuesService: GithubIssuesService
): GithubIssuesRepository {

    override suspend fun fetchIssues(state: String, page: Int): List<GithubIssueModel> {
        return githubIssuesService.fetchIssues(state, page = page).map{
            it.toEntity()
        }
    }
}