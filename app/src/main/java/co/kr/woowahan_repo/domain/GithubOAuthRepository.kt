package co.kr.woowahan_repo.domain

import com.google.gson.annotations.SerializedName

interface GithubOAuthRepository {
    fun getOAuthActionViewUrl(clientId: String, scope: Array<String>): String
    suspend fun requestAccessToken(clientId: String, clientSecret: String, code: String): OAuthAccessTokenInfo
}