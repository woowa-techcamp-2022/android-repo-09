package co.kr.woowahan_repo.domain.model

data class GithubNotification(
    val id: String,
    val repositoryName: String,
    val number: String,
    var updatedAt: String,
    val repositoryImage: String,
    val title: String,
    val comments: String
)
