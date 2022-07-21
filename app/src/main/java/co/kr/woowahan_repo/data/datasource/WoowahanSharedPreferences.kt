package co.kr.woowahan_repo.data.datasource

import android.content.Context
import androidx.core.content.edit
import co.kr.woowahan_repo.domain.datasource.GithubTokenDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WoowahanSharedPreferences @Inject constructor(
    @ApplicationContext private val context: Context
): GithubTokenDataSource {
    companion object {
        private const val spName = "WOOWAHAN"
    }
    private val sp = context.getSharedPreferences(spName, 0)

    private var githubToken: String
        get() = sp.getString("github_token", null) ?: ""
        set(value) = sp.edit { putString("github_token", value) }

    override fun updateToken(token: String) {
        githubToken = token
    }

    override fun fetchToken(): String = githubToken

}