package co.kr.woowahan_repo.domain

interface GithubTokenDataSource {
    fun updateToken(token: String)
    fun fetchToken(): String
}