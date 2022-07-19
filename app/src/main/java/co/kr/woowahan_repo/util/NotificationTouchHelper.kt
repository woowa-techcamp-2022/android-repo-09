package co.kr.woowahan_repo.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import co.kr.woowahan_repo.presentation.ui.notifications.NotificationsAdapter

class NotificationTouchHelper(
    private val notificationsAdapter : NotificationsAdapter
): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        notificationsAdapter.removeItem(viewHolder.adapterPosition)
    }
}