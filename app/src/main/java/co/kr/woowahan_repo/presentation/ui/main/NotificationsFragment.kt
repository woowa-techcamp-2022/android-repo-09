package co.kr.woowahan_repo.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.FragmentNotificationsBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseFragment
import co.kr.woowahan_repo.presentation.ui.notifications.NotificationsAdapter
import co.kr.woowahan_repo.presentation.viewmodel.NotificationsViewModel

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {
    companion object {
        fun newInstance() = NotificationsFragment()
    }
    private val notificationsViewModel by viewModels<NotificationsViewModel>()
    private val notificationsAdapter by lazy { NotificationsAdapter() }

    override val TAG: String
        get() = NotificationsFragment::class.java.simpleName
    override val layoutResId: Int
        get() = R.layout.fragment_notifications

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    override fun onStart() {
        super.onStart()
        notificationsViewModel.fetchNotifications()
    }

    private fun initView() {
        binding.rvNotifications.adapter = notificationsAdapter
    }

    private fun observeData() {
        notificationsViewModel.notifications.observe(viewLifecycleOwner) {
            notificationsAdapter.updateList(it)
        }
    }
}