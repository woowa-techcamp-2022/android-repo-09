package co.kr.woowahan_repo.presentation.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.FragmentNotificationsBinding
import co.kr.woowahan_repo.domain.GithubApiDateFormat
import co.kr.woowahan_repo.presentation.ui.base.BaseFragment
import co.kr.woowahan_repo.presentation.viewmodel.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {
    companion object {
        fun newInstance() = NotificationsFragment()
    }

    override val TAG: String get() = this::class.java.simpleName
    override val layoutResId: Int get() = R.layout.fragment_notifications

    @Inject
    lateinit var dateFormatter: GithubApiDateFormat

    private val notificationsViewModel: NotificationsViewModel by viewModels()
    private val notificationsAdapter by lazy {
        NotificationsAdapter(
            dateFormatter.getSimpleDateFormat()
        ) { id, position ->
            notificationsViewModel.patchNotificationAsRead(id, position)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = notificationsViewModel
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
        notificationsViewModel.notifications.observe(viewLifecycleOwner) {
            notificationsAdapter.updateList(it)
        }
    }
}