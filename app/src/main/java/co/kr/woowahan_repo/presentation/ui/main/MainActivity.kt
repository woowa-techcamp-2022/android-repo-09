package co.kr.woowahan_repo.presentation.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.ActivityMainBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseActivity
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

        viewModel.showTabOneEvent.observe(this) {
            Timber.d("show tab 1")
//            supportFragmentManager.commit {
//                replace(
//                    R.id.container_fragment_main,
//                    Fragment()
//                )
//            }
        }
        viewModel.showTabTwoEvent.observe(this) {
            Timber.d("show tab 2")
//            supportFragmentManager.commit {
//                replace(
//                    R.id.container_fragment_main,
//                    Fragment()
//                )
//            }
        }

        viewModel.showSearchEvent.observe(this){
            Timber.d("show search view")
        }

        viewModel.showProfileEvent.observe(this){
            Timber.d("show profile view")
        }
    }
}