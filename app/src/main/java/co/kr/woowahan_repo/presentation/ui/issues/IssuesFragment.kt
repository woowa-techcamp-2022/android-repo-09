package co.kr.woowahan_repo.presentation.ui.issues

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.application.WoowahanRepoApplication
import co.kr.woowahan_repo.databinding.FragmentIssuesBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseFragment
import co.kr.woowahan_repo.presentation.viewmodel.IssuesViewModel
import co.kr.woowahan_repo.presentation.viewmodel.IssuesViewModelFactory
import com.google.android.material.snackbar.Snackbar

class IssuesFragment: BaseFragment<FragmentIssuesBinding>() {
    override val TAG: String get() = "IssuesFragment"
    override val layoutResId: Int get() = R.layout.fragment_issues

    private lateinit var viewModel: IssuesViewModel
    private val issuesAdapter = IssuesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, IssuesViewModelFactory(
            (requireActivity().application as WoowahanRepoApplication).appContainer.issuesRepository
        ))[IssuesViewModel::class.java]

        setUpRecyclerView()
        setListener()
        setUpViewModel()
    }

    private fun setUpRecyclerView() {
        binding.rvIssues.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = issuesAdapter
        }
    }

    private fun setListener(){
        
    }

    private fun setUpViewModel(){
        viewModel.dataLoading.observe(viewLifecycleOwner){
            binding.progress.isVisible = it
        }

        viewModel.viewState.observe(viewLifecycleOwner){
            when(it){
                is IssuesViewModel.IssuesViewState.Issues -> {
                    issuesAdapter.updateList(it.issues ?: listOf())
                }
                is IssuesViewModel.IssuesViewState.FetchDataFail -> {
                    Snackbar.make(binding.background, it.error?.message ?: "", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}