package co.kr.woowahan_repo.data.repository

import co.kr.woowahan_repo.data.service.GithubIssuesService
import co.kr.woowahan_repo.domain.model.GithubIssueModel
import co.kr.woowahan_repo.domain.repository.GithubIssuesRepository
import javax.inject.Inject

class GithubIssuesRepositoryImpl @Inject constructor(
    private val githubIssuesService: GithubIssuesService
): GithubIssuesRepository {

    override suspend fun fetchIssues(state: String, page: Int): Result<List<GithubIssueModel>> {
        return kotlin.runCatching {
            githubIssuesService.fetchIssues(state, page = page).map{
                it.toEntity()
            }
        }
    }
}