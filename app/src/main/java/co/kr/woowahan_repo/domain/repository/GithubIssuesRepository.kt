package co.kr.woowahan_repo.domain.repository

import co.kr.woowahan_repo.domain.entity.GithubIssueModel

interface GithubIssuesRepository {
    suspend fun fetchIssues(state: String, page: Int): List<GithubIssueModel>
}