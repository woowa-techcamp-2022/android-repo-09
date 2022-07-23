package co.kr.woowahan_repo.data.datasource

import co.kr.woowahan_repo.domain.datasource.GithubProfileDataSource
import javax.inject.Inject

class GithubProfileDataSourceImpl @Inject constructor(): GithubProfileDataSource {

    private var profileUrl: String? = null

    override fun updateProfileUrl(url: String) {
        profileUrl = url
    }

    override fun fetchProfileUrl(): String = profileUrl ?: ""
}