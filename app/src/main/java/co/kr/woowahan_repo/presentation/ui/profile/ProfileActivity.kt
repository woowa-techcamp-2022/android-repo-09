package co.kr.woowahan_repo.presentation.ui.profile

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.ActivityProfileBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseActivity
import co.kr.woowahan_repo.presentation.viewmodel.ProfileViewModel
import co.kr.woowahan_repo.presentation.viewmodel.woowahanViewModelFactory
import co.kr.woowahan_repo.util.showToast
import coil.load
import coil.transform.CircleCropTransformation

class ProfileActivity : BaseActivity<ActivityProfileBinding>() {
    override val layoutResId: Int
        get() = R.layout.activity_profile

    private val viewModel: ProfileViewModel by viewModels{ woowahanViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewmodel = viewModel
        initView()
        initOnClickListener()
        observeData()
    }

    private fun initView() {
        binding.tvLink.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvMail.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun initOnClickListener() {
        binding.tvLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(binding.tvLink.text.toString())
            }
            startActivity(intent)
        }
        binding.tvMail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.tvMail.text.toString()))
            }
            startActivity(intent)
        }
    }

    private fun observeData() {
        viewModel.profile.observe(this) {
            binding.ivProfile.load(it.profileImage) {
                transformations(CircleCropTransformation())
            }
        }
        viewModel.finishEvent.observe(this) {
            showToast(it)
            finish()
        }
    }
}