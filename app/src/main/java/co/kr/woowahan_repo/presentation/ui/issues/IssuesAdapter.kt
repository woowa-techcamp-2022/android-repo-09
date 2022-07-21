package co.kr.woowahan_repo.presentation.ui.issues

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.ViewIssueItemBinding
import co.kr.woowahan_repo.domain.model.GithubIssueModel
import co.kr.woowahan_repo.util.DateUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * https://sohee1702.tistory.com/409
 *
 * 공강 : O(N)
 * 시간 : O(N + D^2)
 * => old/new 두 리스트의 합인 N개 / old가 new로 변환되기 위해 필요한 최소 작업갯수(==edit script) D
 * 최대 사이즈는 2^26(67,108,864)개까지 지원
 */
class IssuesAdapter: RecyclerView.Adapter<IssuesAdapter.IssueItemViewHolder>() {

    private val issues = mutableListOf<GithubIssueModel>()
    private fun setList(issues: List<GithubIssueModel>){
        this.issues.clear()
        this.issues.addAll(issues)
    }

    fun updateList(issues: List<GithubIssueModel>){
        CoroutineScope(Dispatchers.Default).launch {
            val diffCallback = IssuesDiffUtilCallback(this@IssuesAdapter.issues, issues)
            val diffRes = DiffUtil.calculateDiff(diffCallback)
            withContext(Dispatchers.Main) {
                this@IssuesAdapter.setList(issues)
                diffRes.dispatchUpdatesTo(this@IssuesAdapter)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueItemViewHolder {
        return IssueItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: IssueItemViewHolder, position: Int) {
        holder.bind(issues[position])
    }

    override fun getItemCount(): Int {
        return issues.size
    }

    class IssueItemViewHolder(
        private val binding: ViewIssueItemBinding
    ): RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): IssueItemViewHolder{
                return IssueItemViewHolder(
                    ViewIssueItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(item: GithubIssueModel)= with(binding){
            val title = "${item.repositoryName} #${item.issueNumber}"
            tvTitle.text = title
            tvContent.text = item.issueTitle
            val stateResId = when(item.state){
                GithubIssueModel.IssueState.Open -> R.drawable.ic_issue_state_open
                GithubIssueModel.IssueState.Closed -> R.drawable.ic_issue_state_closed
                else -> R.drawable.ic_issue_state_error
            }
            ivState.setImageResource(stateResId)
            tvDate.text = DateUtil.getGithubDateInterval(item.lastUpdateDate)
        }
    }
}