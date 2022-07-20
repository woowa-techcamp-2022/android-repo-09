package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.*
import co.kr.woowahan_repo.domain.repository.GithubIssuesRepository
import co.kr.woowahan_repo.domain.model.GithubIssueModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class IssuesViewModel @Inject constructor(
    private val issuesRepository: GithubIssuesRepository
): ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _viewState = MutableLiveData<IssuesViewState>()
    val viewState: LiveData<IssuesViewState> = _viewState

    private var issueState: GithubIssueModel.IssueState = GithubIssueModel.IssueState.Open
    fun changeState(type: String){
        issueState = GithubIssueModel.IssueState.get(type)
        fetchIssues()
    }

    fun fetchIssues(){
        _dataLoading.value = true
        viewModelScope.launch {
            issuesRepository.fetchIssues(
                issueState.key, 1
            ).onSuccess {
                Timber.tag("Api Success").d(it.toString())
                _dataLoading.value = false
                _viewState.value = IssuesViewState.Issues(it)
            }.onFailure {
                Timber.tag("Api Fail").e(it)
                _dataLoading.value = false
                _viewState.value = IssuesViewState.FetchDataFail(Throwable("이슈 목록을 가져오는데 실패하였습니다"))
            }
        }
    }

    sealed class IssuesViewState(
        val issues: List<GithubIssueModel>? = null,
        val error: Throwable? = null
    ) {
        class Issues(issues: List<GithubIssueModel>): IssuesViewState(issues = issues)
        class FetchDataFail(error: Throwable): IssuesViewState(null, error = error)
    }
}
