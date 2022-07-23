package co.kr.woowahan_repo.data.datasource

import co.kr.woowahan_repo.domain.datasource.GithubProfileDataSource

class GithubProfileDataSourceImpl private constructor(): GithubProfileDataSource {
    companion object {
        private var instance: GithubProfileDataSourceImpl? = null
        fun getInstance(): GithubProfileDataSourceImpl {
            if(instance == null)
                instance = GithubProfileDataSourceImpl()
            return instance!!
        }
    }
    private var profileUrl: String? = null

    override fun updateProfileUrl(url: String) {
        profileUrl = url
    }

    override fun fetchProfileUrl(): String = profileUrl ?: ""

}