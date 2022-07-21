package co.kr.woowahan_repo.presentation.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.ViewNotificationItemBinding
import co.kr.woowahan_repo.domain.model.GithubNotification
import co.kr.woowahan_repo.util.DateUtil
import coil.load
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.SimpleDateFormat

class NotificationsAdapter(
    private val dateFormat: SimpleDateFormat,
    private val removeItem: (String, Int) -> Unit
) : RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {
    private var itemList = mutableListOf<GithubNotification>()

    class NotificationViewHolder(private val binding: ViewNotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GithubNotification, dateFormat: SimpleDateFormat) {
            binding.notification = data
            binding.tvDate.text = DateUtil.getDateInterval(data.updatedAt, dateFormat)
            binding.ivRepositoryImage.load(data.repositoryImage) {
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = DataBindingUtil.inflate<ViewNotificationItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.view_notification_item,
            parent,
            false
        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(itemList[position], dateFormat)
    }

    override fun getItemCount(): Int = itemList.size

    fun updateList(newItemList: List<GithubNotification>) {
        CoroutineScope(Dispatchers.Default).launch {
            val diffCallback = DiffUtilCallback(itemList, newItemList)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            withContext(Dispatchers.Main) {
                itemList = newItemList.toMutableList()
                diffResult.dispatchUpdatesTo(this@NotificationsAdapter)
            }
        }
    }

    fun removeItem(position: Int) {
        Timber.tag("removeItem에서의 position").i(position.toString())
        Timber.tag("removeItem에서의 id").i(itemList[position].id)
        removeItem(itemList[position].id, position)
    }

    companion object {
        class DiffUtilCallback(
            private val oldItems: List<GithubNotification>,
            private val newItems: List<GithubNotification>
        ) : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldItems.size
            override fun getNewListSize(): Int = newItems.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldItems[oldItemPosition].id == newItems[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldItems[oldItemPosition] == newItems[newItemPosition]

        }
    }
}