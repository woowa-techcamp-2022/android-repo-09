package co.kr.woowahan_repo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_repo.domain.model.GithubRepositorySearchModel
import co.kr.woowahan_repo.domain.repository.GithubRepositorySearchRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchRepositoryViewModel(
    private val searchRepository: GithubRepositorySearchRepository
): ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _viewState = MutableLiveData<SearchViewState>()
    val viewState: LiveData<SearchViewState> = _viewState

    private var currentPage = 1
    private var prevQuery: String? = null
    private var currentList = mutableListOf<GithubRepositorySearchModel>()
    private var debounceQuery: String? = null
    private val debounceDelayMill = 500L

    /**
     * debounce 를 사용하면서 여러 요청이 들어갈 가능성이 생기는데,
     * 이때 무조건 나중에 들어간 요청이 출력되도록 설정해야 한다
     * block 을 해버리면 순차적이 되어버리기에 요청이 너무 오래걸릴것
     */
    private var prevQueryJob: Job? = null
    private fun searchQuery(query: String) {
        Timber.tag("search query").d(query)
        // 디바운스 처리를 했기때문에, 의도치 않은 요청이 여러개 날아올 경우가 존재. 때문에 loading 체크는 하지 않는다
        val throwable = when {
            query.isBlank() -> Throwable("검색어를 입력해 주세요")
            else -> null
        }
        if(throwable != null){
            _viewState.value = SearchViewState.ErrorMessage(throwable)
            return
        }
        _dataLoading.value = true
        // 이전 요청이 있다면 취소 후 요청
        Timber.tag("query try").d("$query, prev coroutine is Running : ${prevQueryJob?.isActive == true}")
        if(prevQueryJob?.isActive == true)
            prevQueryJob?.cancel()

        prevQueryJob = viewModelScope.launch {
            currentPage = 1
            searchRepository.searchQuery(
                query, currentPage
            ).onSuccess {
                prevQuery = query
                currentList.clear()
                currentList.addAll(it)
                if(currentList.isEmpty())
                    _viewState.value = SearchViewState.ErrorMessage(Throwable("검색 결과가 없습니다"))
                _viewState.value = SearchViewState.SearchResList(it)
                _viewState.value = SearchViewState.SearchScrollToTop()
                fetchGithubSearchLimit()
            }.onFailure {
                it.printStackTrace()
                when(it){
                    is CancellationException -> { // 취소된 요청
                        Timber.tag("cancel prev coroutine").d(it.message)
                    }
                    else -> {
                        _viewState.value = SearchViewState.SearchQueryFail(Throwable("검색을 실패하였습니다"))
                    }
                }
            }.also {
                _dataLoading.value = false
            }
        }
    }

    fun fetchNextPage(){
        val throwable = when {
            prevQuery.isNullOrBlank() -> Throwable("검색어를 입력해 주세요")
            _dataLoading.value == true -> return
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
                _viewState.value = SearchViewState.SearchQueryFail(Throwable("검색을 실패하였습니다"))
            }.also {
                _dataLoading.value = false
            }
        }
    }

    /**
     * for debug
     * 분당 최대 30개가 주어지고, 줄어드는 것을 확인 할 수 있었다
     */
    private fun fetchGithubSearchLimit(){
        viewModelScope.launch {
            searchRepository.fetchSearchLimit()
                .onSuccess {
                    Timber.tag("search limit").d("limit[$it]")
                }.onFailure {
                    it.printStackTrace()
                }
        }
    }

    /**
     * event from view
     */
    fun onTextChanged(text: String){
        val query = text.trim()
        if(query.isBlank()) { // 비어있는 경우는 처리하지 않는다
            debounceQuery = query
            return
        }
        if(debounceQuery == query) return
        debounceQuery = query
        viewModelScope.launch {
            delay(debounceDelayMill)
            if(debounceQuery != query) return@launch
            //do work
            Timber.tag("debounce query").d("$debounceQuery")
            searchQuery(debounceQuery!!)
        }
    }

    sealed class SearchViewState(
        val searchResList: List<GithubRepositorySearchModel>? = null,
        val error: Throwable? = null
    ){
        class ErrorMessage(error: Throwable): SearchViewState(null, error)
        class SearchResList(searchResList: List<GithubRepositorySearchModel>): SearchViewState(searchResList)
        class SearchScrollToTop: SearchViewState(null, null)
        class SearchQueryFail(error: Throwable): SearchViewState(null, error)
    }
}