package co.kr.woowahan_repo.domain.datasource

interface GithubProfileDataSource {
    fun updateProfileUrl(url: String)
    fun fetchProfileUrl() : String
}