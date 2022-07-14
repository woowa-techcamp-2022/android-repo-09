package co.kr.woowahan_repo.presentation.ui.issues

import androidx.recyclerview.widget.DiffUtil
import co.kr.woowahan_repo.domain.entity.GithubIssueModel

class IssuesDiffUtilCallback(
    private val oldList: List<GithubIssueModel>,
    private val newList: List<GithubIssueModel>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].issueTitle == newList[newItemPosition].issueTitle
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}