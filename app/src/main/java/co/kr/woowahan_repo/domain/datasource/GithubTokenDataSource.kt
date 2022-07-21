package co.kr.woowahan_repo.domain.datasource

interface GithubTokenDataSource {
    fun updateToken(token: String)
    fun fetchToken(): String
}