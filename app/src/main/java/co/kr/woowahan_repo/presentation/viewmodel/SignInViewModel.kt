package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.data.model.request.OAuthAccessTokenRequest
import co.kr.woowahan_repo.di.ServiceLocator
import kotlinx.coroutines.launch
import timber.log.Timber

class SignInViewModel : ViewModel() {
    val oAuthAccessTokenService = ServiceLocator.getOAuthAccessTokenService()
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

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
                ServiceLocator.accessToken = it.accessToken
                _isSuccess.value = true
            }.onFailure {
                Timber.tag("Error").e(it)
            }
        }
    }
}