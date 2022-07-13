package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _tabOneSelected = MutableLiveData<Boolean>()
    val tabOneSelected: LiveData<Boolean> = _tabOneSelected

    private val _tabTwoSelected = MutableLiveData<Boolean>()
    val tabTwoSelected: LiveData<Boolean> = _tabTwoSelected

    private val _showTabOneEvent = MutableLiveData<Unit>()
    val showTabOneEvent: LiveData<Unit> = _showTabOneEvent

    private val _showTabTwoEvent = MutableLiveData<Unit>()
    val showTabTwoEvent: LiveData<Unit> = _showTabTwoEvent

    private val _showSearchEvent = MutableLiveData<Unit>()
    val showSearchEvent: LiveData<Unit> = _showSearchEvent

    private val _showProfileEvent = MutableLiveData<Unit>()
    val showProfileEvent: LiveData<Unit> = _showProfileEvent


    fun clickTabOne(){
        _tabOneSelected.value = true
        _tabTwoSelected.value = false
        _showTabOneEvent.value = Unit
    }

    fun clickTabTwo(){
        _tabOneSelected.value = false
        _tabTwoSelected.value = true
        _showTabTwoEvent.value = Unit
    }

    fun clickSearch(){
        _showSearchEvent.value = Unit
    }

    fun clickProfile(){
        _showProfileEvent.value = Unit
    }

}