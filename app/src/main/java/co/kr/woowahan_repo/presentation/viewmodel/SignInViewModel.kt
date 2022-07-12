package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.data.api.ServiceCreator
import co.kr.woowahan_repo.data.model.OAuthAccessTokenRequest
import kotlinx.coroutines.launch
import timber.log.Timber

class SignInViewModel : ViewModel() {
    fun getGithubOAuthAccessToken(code: String) {
        viewModelScope.launch {
            runCatching {
                ServiceCreator.githubOAuthAccessTokenService.requestAccessToken(
                    OAuthAccessTokenRequest(
                        BuildConfig.GITHUB_CLIENT_ID,
                        BuildConfig.GITHUB_SECRETS,
                        code
                    )
                )
            }.onSuccess {
                Timber.tag("Success").d(it.accessToken)
            }.onFailure {
                Timber.tag("Error").e(it)
            }
        }
    }
}