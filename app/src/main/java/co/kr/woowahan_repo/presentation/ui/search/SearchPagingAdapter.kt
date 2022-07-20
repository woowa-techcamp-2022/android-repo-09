package co.kr.woowahan_repo.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.woowahan_repo.databinding.ViewSearchRepositoryItemBinding
import co.kr.woowahan_repo.domain.model.GithubRepositorySearchModel
import co.kr.woowahan_repo.util.ColorUtil
import coil.load

class SearchPagingAdapter(): PagingDataAdapter<GithubRepositorySearchModel, SearchPagingAdapter.SearchRepositoryItemViewHolder>(SearchRepositoryDiffUtilItemCallback()) {

    override fun onBindViewHolder(holder: SearchRepositoryItemViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchRepositoryItemViewHolder {
        return SearchRepositoryItemViewHolder.from(parent)
    }

    class SearchRepositoryItemViewHolder(
        private val binding: ViewSearchRepositoryItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): SearchRepositoryItemViewHolder {
                return SearchRepositoryItemViewHolder(
                    ViewSearchRepositoryItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(searchResponse: GithubRepositorySearchModel)= with(binding){
            ivProfile.load(searchResponse.userProfileImageUrl)

            tvUser.text = searchResponse.user
            tvRepositoryTitle.text = searchResponse.repositoryTitle

            tvRepositoryDescription.isVisible = !searchResponse.repositoryDescriptor.isNullOrBlank()
            tvRepositoryDescription.text = searchResponse.repositoryDescriptor

            tvLanguage.isVisible = !searchResponse.language.isNullOrBlank()
            cardViewLanguage.isVisible = !searchResponse.language.isNullOrBlank()
            tvLanguage.text = searchResponse.language
            if(!searchResponse.language.isNullOrBlank())
                ivLanguage.setBackgroundColor(ColorUtil.getColor(searchResponse.language))

            when{
                searchResponse.starCount<1000 -> tvStar.text = searchResponse.starCount.toString()
                else -> {
                    try{
                        tvStar.text = String.format("%.1f", searchResponse.starCount.toFloat()/1000.0f) + "K"
                    }catch (e: Exception){
                        e.printStackTrace()
                        tvStar.text = searchResponse.starCount.toString()
                    }
                }
            }
        }
    }
}

class SearchRepositoryDiffUtilItemCallback: DiffUtil.ItemCallback<GithubRepositorySearchModel>() {
    override fun areItemsTheSame(
        oldItem: GithubRepositorySearchModel,
        newItem: GithubRepositorySearchModel
    ): Boolean {
        return oldItem.repositoryTitle == newItem.repositoryTitle
    }

    override fun areContentsTheSame(
        oldItem: GithubRepositorySearchModel,
        newItem: GithubRepositorySearchModel
    ): Boolean {
        return oldItem == newItem
    }
}