package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_repo.di.ServiceLocator
import co.kr.woowahan_repo.domain.model.GithubNotification
import co.kr.woowahan_repo.domain.repository.GithubNotificationsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

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
                    Timber.tag("Notifications Success").d(it.toString())
                    _notifications.value = it
                    page++
                }.onFailure {
                    Timber.tag("Notifications Failure").e(it)
                }.also {
                    _isDataLoading.value = false
                }
        }
    }

    fun patchNotificationAsRead(threadId: String) {
        viewModelScope.launch {
            _isDataLoading.value = true
            githubNotificationsRepository.patchNotificationAsRead(threadId)
                .onSuccess {
                    _isPatchSuccess.value = true
                    _notifications.value = requireNotNull(notifications.value).toMutableList()
                        .apply { removeIf { it.id == threadId } }
                        .map { it.copy() }.toList()
                }.onFailure {
                    _isPatchSuccess.value = false
                    _notifications.value =
                        requireNotNull(notifications.value).toList().map { it.copy() }.toList()
                }.also {
                    _isDataLoading.value = false
                }
        }
    }
}