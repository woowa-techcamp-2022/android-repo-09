package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import co.kr.woowahan_repo.application.WoowahanRepoApplication
import timber.log.Timber

/**
 *  Factory for all ViewModels
 */

val woowahanViewModelFactory = object: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Timber.d("viewModelFactory debug 1 $modelClass")
        return super.create(modelClass)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T = with(modelClass) {
        Timber.d("viewModelFactory debug 2 $modelClass")
        val application = checkNotNull(extras[APPLICATION_KEY]) as WoowahanRepoApplication
        when {
            isAssignableFrom(SignInViewModel::class.java) -> SignInViewModel(application.oAuthRepository)
            isAssignableFrom(MainViewModel::class.java) -> MainViewModel(application.githubProfileRepository)
            isAssignableFrom(IssuesViewModel::class.java) -> IssuesViewModel(application.issueRepository)
            isAssignableFrom(NotificationsViewModel::class.java) -> NotificationsViewModel(application.notificationRepository)
            isAssignableFrom(SearchRepositoryViewModel::class.java) -> SearchRepositoryViewModel(application.githubRepositorySearchRepository)
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T
    }
}