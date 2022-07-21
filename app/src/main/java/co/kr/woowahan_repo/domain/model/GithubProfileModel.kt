package co.kr.woowahan_repo.domain.model

data class GithubProfileModel(
    val userName: String,
    val profileImage: String,
    val userDescriptor: String?,
    val job: String?,
    val location: String?,
    val blogAddress: String?,
    val emailAddress: String?,
    val followerCount: Int,
    val followingCount: Int,
    val repositoryCount: Int,
    var starCount: Int = -1
)