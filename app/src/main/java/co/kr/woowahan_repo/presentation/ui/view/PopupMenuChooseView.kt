package co.kr.woowahan_repo.presentation.ui.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import android.widget.PopupMenu
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.ViewPopUpMenuChooseBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseConstraintCustomView

class PopupMenuChooseView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): BaseConstraintCustomView<ViewPopUpMenuChooseBinding>(context, attrs, defStyleAttr, defStyleRes) {
    override val layoutResId: Int get() = R.layout.view_pop_up_menu_choose
    constructor(context: Context): this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0, 0)

    private var popupMenu: PopupMenu? = null

    init {
        attrs.let {
            val typedArr = context.obtainStyledAttributes(it, R.styleable.popupMenuChooseView, 0, 0)
            val title = typedArr.getString(R.styleable.popupMenuChooseView_pop_up_menu_title)
            val defaultValue = typedArr.getString(R.styleable.popupMenuChooseView_pop_up_menu_default_value)
            if(!title.isNullOrBlank()) binding.tvTitle.text = title
            if(!defaultValue.isNullOrBlank()) binding.tvValue.text = defaultValue
            typedArr.recycle()
        }
        setListener()
    }

    private fun setListener()= with(binding){
        layoutFilter.setOnClickListener {
            popupMenu?.show()
            setActiveMode(true)
        }
    }

    fun setUpPopupMenu(activity: Activity, menuResId: Int, itemSelectListener: (MenuItem) -> (Boolean)){
        popupMenu = PopupMenu(activity, binding.ivOptionArrow)
        activity.menuInflater.inflate(menuResId, popupMenu?.menu)
        popupMenu?.setOnMenuItemClickListener {
            binding.tvValue.text = it.title
            itemSelectListener(it)
        }
        popupMenu?.setOnDismissListener {
            setActiveMode(false)
        }
    }

    private fun setActiveMode(isActive: Boolean)= with(binding){
        when(isActive){
            true -> {
                ivOptionArrow.setImageResource(R.drawable.ic_selector_arrow_up)
                layoutFilter.setBackgroundResource(R.drawable.bg_issue_state_filter_active)
            }
            false -> {
                ivOptionArrow.setImageResource(R.drawable.ic_selector_arrow_down)
                layoutFilter.setBackgroundResource(R.drawable.bg_issue_state_filter)
            }
        }
    }
}