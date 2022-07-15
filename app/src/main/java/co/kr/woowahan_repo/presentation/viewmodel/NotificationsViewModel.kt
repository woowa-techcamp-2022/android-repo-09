package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_repo.di.ServiceLocator
import co.kr.woowahan_repo.domain.entity.GithubNotification
import kotlinx.coroutines.launch
import timber.log.Timber

class NotificationsViewModel : ViewModel() {
    private val notificationsRepository = ServiceLocator.getNotificationsRepository()
    private var page = 1

    private val _notifications = MutableLiveData<List<GithubNotification>>()
    val notifications: LiveData<List<GithubNotification>> get() = _notifications

    fun fetchNotifications() {
        viewModelScope.launch {
            runCatching {
                notificationsRepository.fetchNotifications(page)
            }.onSuccess {
                Timber.tag("Notifications Success").d(it.toString())
                _notifications.value = it
                page++
            }.onFailure {
                Timber.tag("Notifications Failure").e(it)
            }
        }
    }
}