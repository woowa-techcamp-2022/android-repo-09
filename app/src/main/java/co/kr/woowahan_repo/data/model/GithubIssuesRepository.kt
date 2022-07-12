package co.kr.woowahan_repo.data.model

import co.kr.woowahan_repo.domain.GithubIssueModel

interface GithubIssuesRepository {
    suspend fun fetchIssues(state: String, page: Int): List<GithubIssueModel>
}