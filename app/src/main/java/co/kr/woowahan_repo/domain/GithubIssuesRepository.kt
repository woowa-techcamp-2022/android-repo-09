package co.kr.woowahan_repo.domain

interface GithubIssuesRepository {
    suspend fun fetchIssues(state: String, page: Int): List<GithubIssueModel>
}