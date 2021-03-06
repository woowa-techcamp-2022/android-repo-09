package co.kr.woowahan_repo.domain.repository

import co.kr.woowahan_repo.domain.model.OAuthAccessTokenInfo

interface GithubOAuthRepository {
    fun getOAuthActionViewUrl(clientId: String, scope: Array<String>): Result<String>
    suspend fun requestAccessToken(clientId: String, clientSecret: String, code: String): Result<OAuthAccessTokenInfo>
}