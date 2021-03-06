package co.kr.woowahan_repo.presentation.ui.issues

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.FragmentIssuesBinding
import co.kr.woowahan_repo.domain.GithubApiDateFormat
import co.kr.woowahan_repo.domain.model.GithubIssueModel
import co.kr.woowahan_repo.presentation.ui.base.BaseFragment
import co.kr.woowahan_repo.presentation.viewmodel.IssuesViewModel
import co.kr.woowahan_repo.util.scrollToTop
import co.kr.woowahan_repo.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class IssuesFragment: BaseFragment<FragmentIssuesBinding>() {
    companion object {
        fun newInstance() = IssuesFragment()
    }
    override val TAG: String get() = this::class.java.simpleName
    override val layoutResId: Int get() = R.layout.fragment_issues

    @Inject
    lateinit var dateFormatter: GithubApiDateFormat

    private val viewModel: IssuesViewModel by viewModels()
    private val issuesAdapter by lazy { IssuesAdapter(dateFormatter.getSimpleDateFormat()) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setListener()
        observeData()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchIssues()
    }

    private fun setUpRecyclerView() {
        binding.rvIssues.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                setDrawable(context.resources.getDrawable(R.drawable.stroke_issue_item_divider, null))
            })
            adapter = issuesAdapter
        }
    }

    private fun setListener()= with(binding){
        popUpMenuChooseView.setUpPopupMenu(
            requireActivity(),
            R.menu.menu_issue_state,
            R.style.issueFilterPopupMenu
        ) {
            val state = when(it.itemId){
                R.id.menu_issue_state_open -> {
                    Timber.tag("PopupMenu Issue State Click").d("open")
                    GithubIssueModel.IssueState.Open.key
                }
                R.id.menu_issue_state_closed -> {
                    Timber.tag("PopupMenu Issue State Click").d("closed")
                    GithubIssueModel.IssueState.Closed.key
                }
                R.id.menu_issue_state_all -> {
                    Timber.tag("PopupMenu Issue State Click").d("all")
                    GithubIssueModel.IssueState.All.key
                }
                else -> null
            }
            viewModel.changeState(requireNotNull(state))
            true
        }
    }

    private fun observeData(){
        viewModel.dataLoading.observe(viewLifecycleOwner){
            binding.progress.isVisible = it
        }

        viewModel.viewState.observe(viewLifecycleOwner){
            when(it){
                is IssuesViewModel.IssuesViewState.Issues -> {
                    issuesAdapter.updateList(it.issues ?: listOf())
                    binding.rvIssues.scrollToTop(false)
                }
                is IssuesViewModel.IssuesViewState.FetchDataFail -> {
                    showSnackBar(binding.background, it.error?.message ?: "")
                }
            }
        }
    }
}