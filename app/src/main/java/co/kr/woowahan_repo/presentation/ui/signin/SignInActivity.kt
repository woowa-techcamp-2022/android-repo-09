package co.kr.woowahan_repo.presentation.ui.signin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
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
    private val signInViewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnSignIn.setOnClickListener {
            val url = "https://github.com/login/oauth/authorize" +
                    "?client_id=${BuildConfig.GITHUB_CLIENT_ID}&scope=repo, notifications"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }

        signInViewModel.isSuccess.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
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
            signInViewModel.getGithubOAuthAccessToken(code)
        }
    }
}