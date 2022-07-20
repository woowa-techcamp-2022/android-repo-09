package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_repo.data.SingleEvent
import co.kr.woowahan_repo.domain.repository.GithubProfileRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val profileRepository: GithubProfileRepository
): ViewModel() {
    private val _tabOneSelected = MutableLiveData<Boolean>()
    val tabOneSelected: LiveData<Boolean> = _tabOneSelected

    private val _tabTwoSelected = MutableLiveData<Boolean>()
    val tabTwoSelected: LiveData<Boolean> = _tabTwoSelected

    private val _showTabOneEvent = MutableLiveData<SingleEvent<Unit>>()
    val showTabOneEvent: LiveData<SingleEvent<Unit>> = _showTabOneEvent

    private val _showTabTwoEvent = MutableLiveData<SingleEvent<Unit>>()
    val showTabTwoEvent: LiveData<SingleEvent<Unit>> = _showTabTwoEvent

    private val _showSearchEvent = MutableLiveData<SingleEvent<Unit>>()
    val showSearchEvent: LiveData<SingleEvent<Unit>> = _showSearchEvent

    private val _showProfileEvent = MutableLiveData<SingleEvent<Unit>>()
    val showProfileEvent: LiveData<SingleEvent<Unit>> = _showProfileEvent

    private val _profileUrl = MutableLiveData<String>()
    val profileUrl: LiveData<String> = _profileUrl


    fun fetchProfileUrl(){
        Timber.d("fetchProfileUrl")
        viewModelScope.launch {
            profileRepository.fetchProfileUrl()
                .onSuccess {
                    _profileUrl.value = it
                }.onFailure {
                    it.printStackTrace()
                }
        }
    }

    /**
     * event from view
     */
    fun clickTabOne(){
        _tabOneSelected.value = true
        _tabTwoSelected.value = false
        _showTabOneEvent.value = SingleEvent(Unit)
    }

    fun clickTabTwo(){
        _tabOneSelected.value = false
        _tabTwoSelected.value = true
        _showTabTwoEvent.value = SingleEvent(Unit)
    }

    fun clickSearch(){
        _showSearchEvent.value = SingleEvent(Unit)
    }

    fun clickProfile(){
        _showProfileEvent.value = SingleEvent(Unit)
    }

}