package co.kr.woowahan_repo.data.datasource

import android.content.Context
import androidx.core.content.edit
import co.kr.woowahan_repo.domain.datasource.GithubProfileDataSource
import co.kr.woowahan_repo.domain.datasource.GithubTokenDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WoowahanSharedPreferences @Inject constructor(
    @ApplicationContext private val context: Context
): GithubTokenDataSource, GithubProfileDataSource {
    companion object {
        private const val spName = "WOOWAHAN"
    }
    private val sp = context.getSharedPreferences(spName, 0)

    private var githubToken: String
        get() = sp.getString("github_token", null) ?: ""
        set(value) = sp.edit { putString("github_token", value) }

    private var githubProfileUrl: String
        get() = sp.getString("github_profile_url", null) ?: ""
        set(value) = sp.edit { putString("github_profile_url", value) }

    override fun updateToken(token: String) {
        githubToken = token
    }

    override fun fetchToken(): String = githubToken

    override fun updateProfileUrl(url: String) {
        githubProfileUrl = url
    }

    override fun fetchProfileUrl(): String = githubProfileUrl

}