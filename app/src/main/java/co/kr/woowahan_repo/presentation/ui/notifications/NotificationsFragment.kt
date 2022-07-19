package co.kr.woowahan_repo.presentation.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.FragmentNotificationsBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseFragment
import co.kr.woowahan_repo.presentation.viewmodel.NotificationsViewModel
import co.kr.woowahan_repo.util.NotificationTouchHelper
import timber.log.Timber

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {
    companion object {
        fun newInstance() = NotificationsFragment()
    }
    private val notificationsViewModel by viewModels<NotificationsViewModel>()
    private val notificationsAdapter = NotificationsAdapter { id, position ->
        Timber.tag("removeItem에서 전달한 id").i(id)
        notificationsViewModel.patchNotificationAsRead(id, position)
    }

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
        val notificationTouchHelper = NotificationTouchHelper(notificationsAdapter)
        ItemTouchHelper(notificationTouchHelper).attachToRecyclerView(binding.rvNotifications)
    }

    private fun observeData() {
        notificationsViewModel.isDataLoading.observe(viewLifecycleOwner) {
            if(it) { binding.progress.visibility = View.VISIBLE }
            else { binding.progress.visibility = View.GONE }
        }
        notificationsViewModel.notifications.observe(viewLifecycleOwner) {
            Timber.tag("removeItem observe?").i("observed")
            Timber.tag("after removeItem, itemSize = ").i(it.size.toString())
            notificationsAdapter.updateList(it)
        }
    }
}