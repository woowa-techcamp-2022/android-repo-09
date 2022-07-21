package co.kr.woowahan_repo.presentation.ui.signin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.ActivitySignInBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseActivity
import co.kr.woowahan_repo.presentation.ui.main.MainActivity
import co.kr.woowahan_repo.presentation.viewmodel.SignInViewModel
import co.kr.woowahan_repo.presentation.viewmodel.woowahanViewModelFactory
import timber.log.Timber

class SignInActivity : BaseActivity<ActivitySignInBinding>() {
    override val layoutResId: Int
        get() = R.layout.activity_sign_in
    private val viewModel: SignInViewModel by viewModels{ woowahanViewModelFactory }

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
                is SignInViewModel.SignInViewState.ActionViewOAuthUrlFail -> {

                }
                is SignInViewModel.SignInViewState.OAuthSuccess -> {
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                }
                is SignInViewModel.SignInViewState.OAuthFail -> {
                    Toast.makeText(applicationContext, it.error.toString(), Toast.LENGTH_LONG).show()
                }
                else -> {
                    throw IllegalStateException()
                }
            }

        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Toast.makeText(applicationContext, "Github 로그인", Toast.LENGTH_LONG).show()
        Timber.d("onNewIntent called => data[${intent?.data}], dataString[${intent?.dataString}]")
        intent?.data?.let { processUrlScheme(it) }
    }

    private fun processUrlScheme(uri: Uri){
        val scheme = uri.scheme
        val code = uri.getQueryParameter("code")
        Timber.tag("process url scheme").d("scheme[$scheme], code[$code]")
        viewModel.getGithubOAuthAccessToken(scheme, code)
    }
}