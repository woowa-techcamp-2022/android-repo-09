package co.kr.woowahan_repo.domain

data class GithubIssueModel(
    val repositoryName: String,
    val issueTitle: String,
    val state: String
) {

}