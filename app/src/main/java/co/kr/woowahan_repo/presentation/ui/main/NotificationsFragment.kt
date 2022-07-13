package co.kr.woowahan_repo.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.viewModels
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.FragmentNotificationsBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseFragment
import co.kr.woowahan_repo.presentation.viewmodel.NotificationsViewModel

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {
    private val notificationsViewModel by viewModels<NotificationsViewModel>()

    override val TAG: String
        get() = NotificationsFragment::class.java.simpleName
    override val layoutResId: Int
        get() = R.layout.fragment_notifications

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationsViewModel.fetchNotifications()
    }

    companion object {
        fun newInstance() = NotificationsFragment()
    }
}