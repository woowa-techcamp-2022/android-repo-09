package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_repo.domain.model.GithubProfileModel
import co.kr.woowahan_repo.domain.repository.GithubProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val githubProfileRepository: GithubProfileRepository
) : ViewModel() {
    private val _dataLoading: MutableLiveData<Boolean> = MutableLiveData()
    val dataLoading: LiveData<Boolean> = _dataLoading
    private val _profile = MutableLiveData<GithubProfileModel>()
    val profile: LiveData<GithubProfileModel> get() = _profile
    private val _finishEvent = MutableLiveData<String>()
    val finishEvent: LiveData<String> get() = _finishEvent

    init {
        viewModelScope.launch {
            _dataLoading.value = true
            githubProfileRepository.fetchProfile()
                .onSuccess { _profile.value = it }
                .onFailure { _finishEvent.value = "프로필을 가져오는 데 실패했습니다." }
                .also { _dataLoading.value = false }
        }
    }
}