package co.kr.woowahan_repo.presentation.ui.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseConstraintCustomView<T: ViewDataBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    abstract val layoutResId: Int
    protected lateinit var binding: T

    init {
        init()
    }

    private fun init(){
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutResId, this, true)
    }
}