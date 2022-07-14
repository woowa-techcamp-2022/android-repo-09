package co.kr.woowahan_repo.presentation.ui.search

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.application.util.PagingListener
import co.kr.woowahan_repo.databinding.ActivitySearchBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseActivity
import co.kr.woowahan_repo.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_search
    private val viewModel: SearchViewModel by viewModels()

    private val searchAdapter = SearchRepositoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpHeader()
        setUpRecyclerView()
        setListener()
        observeData()

        // 이런건 커스텀 뷰로 생성했다면 뷰 내부 코드로 존재하는 것이니 activity 에 유지
        binding.etSearch.let {
            it.requestFocus()
            setKeyboardShown(true, it)
        }
    }

    private fun setUpHeader(){
        setSupportActionBar(binding.toolBar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_selector_arrow_left) // 뒤로가기 버튼 이미지 설정
        }
    }

    private fun setUpRecyclerView(){
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                setDrawable(context.resources.getDrawable(R.drawable.stroke_issue_item_divider, null))
            })
            adapter = searchAdapter
        }
    }

    private fun setListener()= with(binding){
        layoutBackground.setOnClickListener {
            setKeyboardShown(false, it)
        }

        etSearch.addTextChangedListener { // 일단 임시
            tlSearch.isStartIconVisible = it.isNullOrBlank() // 이런건 커스텀 뷰로 생성했다면 뷰 내부 코드로 존재하는 것이니 activity 에 유지
        }

        etSearch.setOnKeyListener { v, _, keyEvent ->
            when(keyEvent.keyCode){
                KeyEvent.KEYCODE_ENTER -> {
                    viewModel.searchQuery(etSearch.text.toString())
                    setKeyboardShown(false, v) // 이런건 커스텀 뷰로 생성했다면 뷰 내부 코드로 존재하는 것이니 activity 에 유지
                }
            }
            false // true 하면 이벤트 소비
        }
    }

    private fun observeData(){
        viewModel.dataLoading.observe(this){
            binding.progress.isVisible = it
        }

        viewModel.viewState.observe(this){
            when(it){
                is SearchViewModel.SearchViewState.ErrorMessage -> {
                    Toast.makeText(applicationContext, it.error?.message ?: "", Toast.LENGTH_SHORT).show()
                }

                is SearchViewModel.SearchViewState.SearchResList -> {
                    binding.layoutEmptyResponse.isVisible = it.searchResList.isNullOrEmpty()
                    /**
                     * update List 에서 withContext 코루틴을 사용하기 위해 suspend 함수로 설정했는데
                     * 해당 함수는 scope 내부에서만 호출이 가능한건지 에러가 떠서 일단 임시로 추가해 놓은 코드
                     * 코루틴 학습 후에 수정될 가능성이 있는 코드
                     */
                    lifecycleScope.launch {
                        searchAdapter.updateList(it.searchResList ?: listOf())
                    }
                }
                is SearchViewModel.SearchViewState.SearchQueryFail -> {
                    Timber.tag("search fail").d(it.error?.message)
                    binding.layoutEmptyResponse.isVisible = true
                    Toast.makeText(applicationContext, it.error?.message ?: "", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setKeyboardShown(shown: Boolean, view: View){
        val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        if(shown) // flag 0은 추가동작이 없음을 알림
            imm.showSoftInput(view, 0)
        else {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}