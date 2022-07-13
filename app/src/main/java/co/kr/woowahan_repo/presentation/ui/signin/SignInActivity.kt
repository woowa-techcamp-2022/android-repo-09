package co.kr.woowahan_repo.presentation.ui.signin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.ActivitySignInBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseActivity
import co.kr.woowahan_repo.presentation.ui.main.MainActivity
import co.kr.woowahan_repo.presentation.viewmodel.SignInViewModel
import timber.log.Timber

class SignInActivity : BaseActivity<ActivitySignInBinding>() {
    override val layoutResId: Int
        get() = R.layout.activity_sign_in
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setListener()
        observeData()
    }

    private fun setListener()= with(binding){
        btnSignIn.setOnClickListener {
            viewModel.clickSignIn()
        }
    }

    private fun observeData(){
        viewModel.dataLoading.observe(this){
            binding.progress.isVisible = it
        }

        viewModel.viewState.observe(this) {
            when(it){
                is SignInViewModel.SignInViewState.ActionViewOAuthUrl -> {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
                }
                is SignInViewModel.SignInViewState.OAuthSuccess -> {
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                }
                is SignInViewModel.SignInViewState.OAuthFail -> {
                    Toast.makeText(applicationContext, it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Toast.makeText(applicationContext, "onNewIntent", Toast.LENGTH_LONG).show()
        Timber.d("onNewIntent called => ${intent?.dataString}")
        intent?.dataString?.let {
            val start = it.indexOf("code")
            Timber.d("start => $start")
            val code = it.substring(start+5, it.length)
            Timber.d("code => $code")
            viewModel.getGithubOAuthAccessToken(code)
        }
    }
}