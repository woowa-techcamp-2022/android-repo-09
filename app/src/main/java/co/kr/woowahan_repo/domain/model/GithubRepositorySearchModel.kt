package co.kr.woowahan_repo.domain.model

data class GithubRepositorySearchModel(
    val repositoryTitle: String,
    val repositoryDescriptor: String?,
    val user: String,
    val userProfileImageUrl: String,
    val language: String?,
    val starCount: Int
)