package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.data.model.request.OAuthAccessTokenRequest
import co.kr.woowahan_repo.di.ServiceLocator
import kotlinx.coroutines.launch
import timber.log.Timber

class SignInViewModel : ViewModel() {
    val oAuthAccessTokenService = ServiceLocator.getOAuthAccessTokenService()

    fun getGithubOAuthAccessToken(code: String) {
        viewModelScope.launch {
            runCatching {
                oAuthAccessTokenService.requestAccessToken(
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