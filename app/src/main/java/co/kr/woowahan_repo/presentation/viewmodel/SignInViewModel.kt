package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.domain.datasource.GithubTokenDataSource
import co.kr.woowahan_repo.domain.repository.GithubOAuthRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class SignInViewModel(
    private val oAuthRepository: GithubOAuthRepository,
    private val githubTokenDataSource: GithubTokenDataSource
) : ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _viewState = MutableLiveData<SignInViewState>()
    val viewState: LiveData<SignInViewState> = _viewState

    private val appScheme = "camp-09"

    fun getGithubOAuthAccessToken(scheme: String?, code: String?) {
        when {
            scheme.isNullOrBlank() || scheme != appScheme -> {
                _viewState.value = SignInViewState.OAuthFail(Throwable("호출 경로가 유효하지 않습니다"))
                return
            }
            code.isNullOrBlank() -> {
                _viewState.value = SignInViewState.OAuthFail(Throwable("인증 코드가 유효하지 않습니다"))
                return
            }
        }
        _dataLoading.value = true
        viewModelScope.launch {
            oAuthRepository.requestAccessToken(
                BuildConfig.GITHUB_CLIENT_ID,
                BuildConfig.GITHUB_SECRETS,
                code!!
            ).onSuccess {
                Timber.tag("Success").d(it.accessToken)
                _dataLoading.value = false
                githubTokenDataSource.updateToken(it.accessToken)
                _viewState.value = SignInViewState.OAuthSuccess()
            }.onFailure {
                Timber.tag("Error").e(it)
                _dataLoading.value = false
                _viewState.value = SignInViewState.OAuthFail(Throwable("인증 단계를 실패하였습니다"))
            }
        }
    }

    fun clickSignIn(){
        oAuthRepository.getOAuthActionViewUrl(
            BuildConfig.GITHUB_CLIENT_ID,
            arrayOf(
                "repo",
                "notifications",
                "user"
            )
        ).onSuccess {
            Timber.tag("oauth url").d(it)
            _viewState.value = SignInViewState.ActionViewOAuthUrl(it)
        }.onFailure {
            it.printStackTrace()
            _viewState.value = SignInViewState.ActionViewOAuthUrlFail(it)
        }

    }

    sealed class SignInViewState(
        val url: String? = null,
        val error: Throwable? = null
    ) {
        class ActionViewOAuthUrlFail(error: Throwable): SignInViewState(null, error)
        class ActionViewOAuthUrl(url: String): SignInViewState(url, null)
        class OAuthSuccess(): SignInViewState(null)
        class OAuthFail(error: Throwable): SignInViewState(null, error)
    }
}