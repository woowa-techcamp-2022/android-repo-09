package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_repo.di.ServiceLocator
import co.kr.woowahan_repo.domain.model.GithubNotification
import co.kr.woowahan_repo.domain.repository.GithubNotificationsRepository
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val githubNotificationsRepository: GithubNotificationsRepository = ServiceLocator.getNotificationsRepository()
) : ViewModel() {
    private var page = 1

    private val _isDataLoading = MutableLiveData<Boolean>()
    val isDataLoading: LiveData<Boolean> get() = _isDataLoading
    private val _notifications = MutableLiveData<List<GithubNotification>>()
    val notifications: LiveData<List<GithubNotification>> get() = _notifications
    private val _isPatchSuccess = MutableLiveData<Boolean>()
    val isPatchSuccess: LiveData<Boolean> get() = _isPatchSuccess

    fun fetchNotifications() {
        viewModelScope.launch {
            _isDataLoading.value = true
            githubNotificationsRepository.fetchNotifications(page)
                .onSuccess {
                    _notifications.value = it
                    page++
                }.onFailure {
                }.also {
                    _isDataLoading.value = false
                }
        }
    }

    fun patchNotificationAsRead(threadId: String, position: Int) {
        viewModelScope.launch {
            _isDataLoading.value = true
            if (githubNotificationsRepository.patchNotificationAsRead(threadId)) {
                _isPatchSuccess.value = true
                val list = requireNotNull(notifications.value).toMutableList()
                list.removeAt(position)
                _notifications.value = list
            } else {
                _isPatchSuccess.value = false
                _notifications.value =
                    requireNotNull(notifications.value).toList().map { it.copy() }
            }.also { _isDataLoading.value = false }
        }
    }
}