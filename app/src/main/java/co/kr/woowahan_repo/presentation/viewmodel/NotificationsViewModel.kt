package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_repo.di.ServiceLocator
import kotlinx.coroutines.launch
import timber.log.Timber

class NotificationsViewModel: ViewModel() {
    private val notificationsService = ServiceLocator.getNotificationsService()

    fun fetchNotifications() {
        viewModelScope.launch {
            runCatching {
                notificationsService.fetchNotifications(1, 100)
            }.onSuccess {
                Timber.tag("Notifications Success").d(it.toString())
            }.onFailure {
                Timber.tag("Notifications Failure").e(it)
            }
        }
    }
}