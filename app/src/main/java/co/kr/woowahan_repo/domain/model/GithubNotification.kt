package co.kr.woowahan_repo.domain.model

data class GithubNotification(
    val id: String,
    val reason: String,
    val repositoryName: String,
    val number: String,
    val updatedAt: String,
    val repositoryImage: String,
    val title: String,
    val url: String,
    var comments: Int = 0
)
