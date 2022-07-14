package co.kr.woowahan_repo.presentation.ui.search

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.ActivitySearchBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseActivity
import co.kr.woowahan_repo.presentation.viewmodel.SearchViewModel

class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_search
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setListener()
        observeData()
        binding.etSearch.let {
            it.requestFocus()
            setKeyboardShown(true, it)
        }
    }

    private fun initView(){
        setSupportActionBar(binding.toolBar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_selector_arrow_left) // 뒤로가기 버튼 이미지 설정
        }
    }

    private fun setListener()= with(binding){
        layoutBackground.setOnClickListener {
            setKeyboardShown(false, it)
        }

        etSearch.addTextChangedListener { // 일단 임시
            tlSearch.isStartIconVisible = it.isNullOrBlank() // 이런건 커스텀 뷰로 생성했다면 뷰 내부 코드로 존재하는 것이니 activity 에 유지
            viewModel.onTextChanged(it.toString())
        }
    }

    private fun observeData(){

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