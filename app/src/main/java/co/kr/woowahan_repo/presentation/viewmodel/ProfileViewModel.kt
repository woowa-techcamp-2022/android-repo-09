package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_repo.di.ServiceLocator
import co.kr.woowahan_repo.domain.model.GithubProfileModel
import co.kr.woowahan_repo.domain.repository.GithubProfileRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel(
    private val githubProfileRepository: GithubProfileRepository = ServiceLocator.getGithubProfileRepository()
) : ViewModel() {
    private val _profile = MutableLiveData<GithubProfileModel>()
    val profile: LiveData<GithubProfileModel> get() = _profile
    private val _profileUrl = MutableLiveData<String>()
    val profileUrl: LiveData<String> get() = _profileUrl

    init {
        viewModelScope.launch {
            githubProfileRepository.fetchProfile()
                .onSuccess { _profile.value = it }
                .onFailure { Timber.e(it) }
            githubProfileRepository.fetchProfileUrl()
                .onSuccess { _profileUrl.value = it }
                .onFailure { Timber.e(it) }
        }
    }
}