package co.kr.woowahan_repo.presentation.ui.main

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.activity.viewModels
import androidx.fragment.app.commit
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.ActivityMainBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseActivity
import co.kr.woowahan_repo.presentation.ui.issues.IssuesFragment
import co.kr.woowahan_repo.presentation.viewmodel.MainViewModel
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResId: Int
        get() = R.layout.activity_main
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListener()
        observeData()
        if(savedInstanceState == null){ // 기기 회전 등 이벤트로 reCreate 하는 중이 아님
            viewModel.clickTabOne() // default select tab
        }
    }

    private fun setListener()= with(binding){
        btnTab1.setOnClickListener {
            viewModel.clickTabOne()
        }

        btnTab2.setOnClickListener {
            viewModel.clickTabTwo()
        }

        ivSearch.setOnClickListener {
            viewModel.clickSearch()
        }

        ivProfile.setOnClickListener {
            viewModel.clickProfile()
        }
    }

    private fun observeData(){
        viewModel.tabOneSelected.observe(this) {
            binding.btnTab1.isSelected = it
        }
        viewModel.tabTwoSelected.observe(this) {
            binding.btnTab2.isSelected = it
        }

        viewModel.showTabOneEvent.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                Timber.d("show tab 1")
                supportFragmentManager.commit {
                    replace(
                        R.id.container_fragment_main,
                        IssuesFragment.newInstance()
                    )
                }
            }
        }
        viewModel.showTabTwoEvent.observe(this) {  event ->
            event.getContentIfNotHandled()?.let {
                Timber.d("show tab 2")
                supportFragmentManager.commit {
                    replace(
                        R.id.container_fragment_main,
                        NotificationsFragment.newInstance()
                    )
                }
            }
        }

        viewModel.showSearchEvent.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                Timber.d("show search view")
            }
        }

        viewModel.showProfileEvent.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                Timber.d("show profile view")
            }
        }
    }
}