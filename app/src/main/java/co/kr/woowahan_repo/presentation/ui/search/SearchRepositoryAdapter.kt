package co.kr.woowahan_repo.presentation.ui.search


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.woowahan_repo.databinding.ViewSearchRepositoryItemBinding
import co.kr.woowahan_repo.domain.model.GithubRepositorySearchModel
import co.kr.woowahan_repo.util.ColorUtil
import coil.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchRepositoryAdapter: RecyclerView.Adapter<SearchRepositoryAdapter.SearchRepositoryItemViewHolder>() {

    private val repositoryList = mutableListOf<GithubRepositorySearchModel>()
    private fun setList(list: List<GithubRepositorySearchModel>){
        repositoryList.clear()
        repositoryList.addAll(list)
    }

    fun updateList(newList: List<GithubRepositorySearchModel>){
        CoroutineScope(Dispatchers.Default).launch {
            val diffCallback = SearchRepositoryDiffUtil(repositoryList, newList)
            val diffRes = DiffUtil.calculateDiff(diffCallback)
            withContext(Dispatchers.Main){
                setList(newList) // must call main thread
                diffRes.dispatchUpdatesTo(this@SearchRepositoryAdapter)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchRepositoryItemViewHolder {
        return SearchRepositoryItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchRepositoryItemViewHolder, position: Int) {
        holder.bind(repositoryList[position])
    }

    override fun getItemCount(): Int {
        return repositoryList.size
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