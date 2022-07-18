package co.kr.woowahan_repo.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.ActivityMainBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseActivity
import co.kr.woowahan_repo.presentation.ui.search.SearchRepositoryActivity
import co.kr.woowahan_repo.presentation.ui.issues.IssuesFragment
import co.kr.woowahan_repo.presentation.ui.notifications.NotificationsFragment
import co.kr.woowahan_repo.presentation.viewmodel.MainViewModel
import coil.load
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

    override fun onStart() {
        super.onStart()
        viewModel.fetchProfileUrl()
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
                    replace<IssuesFragment>(R.id.container_fragment_main)
                }
            }
        }
        viewModel.showTabTwoEvent.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                Timber.d("show tab 2")
                supportFragmentManager.commit {
                    replace<NotificationsFragment>(R.id.container_fragment_main)
                }
            }
        }

        viewModel.profileUrl.observe(this){
            Timber.d("profileUrl get $it")
            binding.ivProfile.load(it)
        }

        viewModel.showSearchEvent.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                Timber.d("show search view")
                startActivity(Intent(this, SearchRepositoryActivity::class.java))
            }
        }

        viewModel.showProfileEvent.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                Timber.d("show profile view")
            }
        }
    }
}