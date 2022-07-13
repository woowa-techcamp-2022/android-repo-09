package co.kr.woowahan_repo.domain

data class OAuthAccessTokenInfo(
    val accessToken: String,
    val scope: String,
    val tokenType: String
)