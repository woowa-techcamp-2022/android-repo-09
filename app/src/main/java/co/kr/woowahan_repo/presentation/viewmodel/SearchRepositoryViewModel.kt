package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_repo.di.ServiceLocator
import co.kr.woowahan_repo.domain.model.GithubRepositorySearchModel
import kotlinx.coroutines.launch

class SearchRepositoryViewModel: ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _viewState = MutableLiveData<SearchViewState>()
    val viewState: LiveData<SearchViewState> = _viewState

    private val searchRepository = ServiceLocator.getGithubSearchRepository()
    private var currentPage = 1
    private var prevQuery: String? = null
    private var currentList = ArrayList<GithubRepositorySearchModel>()

    fun searchQuery(query: String) {
        val throwable = when {
            query.isBlank() -> Throwable("검색어를 입력해 주세요")
            _dataLoading.value == true -> Throwable("로딩중")
            else -> null
        }
        if(throwable != null){
            _viewState.value = SearchViewState.ErrorMessage(throwable)
            return
        }

        _dataLoading.value = true
        viewModelScope.launch {
            currentPage = 1
            searchRepository.searchQuery(
                query, currentPage
            ).onSuccess {
                _dataLoading.value = false
                prevQuery = query
                currentList.clear()
                currentList.addAll(it)
                if(currentList.isEmpty())
                    _viewState.value = SearchViewState.ErrorMessage(Throwable("검색 결과가 없습니다"))
                _viewState.value = SearchViewState.SearchResList(it)
            }.onFailure {
                it.printStackTrace()
                _dataLoading.value = false
                _viewState.value = SearchViewState.SearchQueryFail(Throwable("검색을 실패하였습니다"))
            }
        }
    }

    fun fetchNextPage(){
        val throwable = when {
            prevQuery.isNullOrBlank() -> Throwable("검색어를 입력해 주세요")
            _dataLoading.value == true -> Throwable("로딩중")
            else -> null
        }
        if(throwable != null){
            _viewState.value = SearchViewState.ErrorMessage(throwable)
            return
        }

        _dataLoading.value = true
        viewModelScope.launch {
            searchRepository.searchQuery(
                prevQuery!!, currentPage + 1
            ).onSuccess {
                _dataLoading.value = false
                when(it.isEmpty()) {
                    true -> _viewState.value = SearchViewState.ErrorMessage(Throwable("검색 결과가 없습니다"))
                    else -> {
                        currentPage++
                        currentList.addAll(it)
                        _viewState.value = SearchViewState.SearchResList(ArrayList(currentList))
                    }
                }
            }.onFailure {
                it.printStackTrace()
                _dataLoading.value = false
                _viewState.value = SearchViewState.SearchQueryFail(Throwable("검색을 실패하였습니다"))
            }
        }
    }

    sealed class SearchViewState(
        val searchResList: List<GithubRepositorySearchModel>? = null,
        val error: Throwable? = null
    ){
        class ErrorMessage(error: Throwable): SearchViewState(null, error)
        class SearchResList(searchResList: List<GithubRepositorySearchModel>): SearchViewState(searchResList)
        class SearchQueryFail(error: Throwable): SearchViewState(null, error)
    }
}