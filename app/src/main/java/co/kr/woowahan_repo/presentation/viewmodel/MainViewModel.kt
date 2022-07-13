package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.kr.woowahan_repo.data.SingleEvent

class MainViewModel: ViewModel() {
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