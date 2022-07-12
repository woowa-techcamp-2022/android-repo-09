package co.kr.woowahan_repo.domain

data class GithubIssueModel(
    val repositoryName: String,
    val issueTitle: String,
    val state: String,
    val lastUpdateDate: String // 2011-01-26T19:14:43Z, "yyyy-MM-dd'T'HH:mm:ss'Z'"
) {

}