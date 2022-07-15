package co.kr.woowahan_repo.presentation.ui.search

import androidx.recyclerview.widget.DiffUtil
import co.kr.woowahan_repo.domain.model.GithubRepositorySearchModel

class SearchRepositoryDiffUtil(
    private val oldList: List<GithubRepositorySearchModel>,
    private val newList: List<GithubRepositorySearchModel>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].repositoryTitle == newList[newItemPosition].repositoryTitle
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}