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
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _viewState = MutableLiveData<SignInViewState>()
    val viewState: LiveData<SignInViewState> = _viewState

    private val oAuthRepository = ServiceLocator.getGithubOAuthRepository()

    fun getGithubOAuthAccessToken(code: String) {
        _dataLoading.value = true
        viewModelScope.launch {
            runCatching {
                oAuthRepository.requestAccessToken(
                    BuildConfig.GITHUB_CLIENT_ID,
                    BuildConfig.GITHUB_SECRETS,
                    code
                )
            }.onSuccess {
                Timber.tag("Success").d(it.accessToken)
                _dataLoading.value = false
                ServiceLocator.accessToken = it.accessToken
                _viewState.value = SignInViewState.OAuthSuccess()
            }.onFailure {
                Timber.tag("Error").e(it)
                _dataLoading.value = false
                _viewState.value = SignInViewState.OAuthFail(Throwable("인증 단계를 실패하였습니다"))
            }
        }
    }

    fun clickSignIn(){
        val url = oAuthRepository.getOAuthActionViewUrl(
            BuildConfig.GITHUB_CLIENT_ID,
            arrayOf(
                "repo",
                "notifications"
            )
        )
        Timber.tag("oauth url").d(url)
        _viewState.value = SignInViewState.ActionViewOAuthUrl(url)
    }

    sealed class SignInViewState(
        val url: String? = null,
        val error: Throwable? = null
    ) {
        class ActionViewOAuthUrl(url: String): SignInViewState(url, null)
        class OAuthSuccess(): SignInViewState(null)
        class OAuthFail(error: Throwable): SignInViewState(null, error)
    }
}