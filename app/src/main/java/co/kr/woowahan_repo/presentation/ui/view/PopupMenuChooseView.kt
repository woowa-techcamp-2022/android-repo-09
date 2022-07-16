package co.kr.woowahan_repo.presentation.ui.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.MenuItem
import android.widget.PopupMenu
import co.kr.woowahan_repo.R
import co.kr.woowahan_repo.databinding.ViewPopUpMenuChooseBinding
import co.kr.woowahan_repo.presentation.ui.base.BaseConstraintCustomView

/**
 * Menu res id와 @style/Widget.MaterialComponents.PopupMenu.Overflow 를 상속한 테마(Optional)을 전달받아 팝업 메뉴를 띄워주는 커스텀 뷰
 *
 * 팝업 메뉴 Background
 * android:popupBackground
 *
 * 팝업 메뉴 텍스트 색깔
 * android:textColor
 *
 * 팝업 메뉴 x축 이동
 * android:dropDownHorizontalOffset
 * 팝업 메뉴 y축 이동
 * android:dropDownVerticalOffset
 *
 * 메뉴 ground checkBehavior="single" 일때 나오는 라디오 버튼 디자인
 * android:listChoiceIndicatorSingle
 * 메뉴 ground checkBehavior="all" 일때 나오는 체크박스 디자인
 * android:listChoiceIndicatorMultiple
 * 해당 디자인 색상
 * android:colorSecondary
 *
 * 메뉴 content 와 배경선 사이의 간격 조절(padding 으로 보이기도 함)
 * android:layout_margin
 */
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
            setActiveMode(true)
            popupMenu?.show()
        }
    }

    fun setUpPopupMenu(activity: Activity, menuResId: Int, themeResId: Int = 0, itemSelectListener: (MenuItem) -> (Boolean)){
        popupMenu = when(themeResId){
            0 -> PopupMenu(activity, binding.ivOptionArrow)
            else -> {
                val wrapper: Context = ContextThemeWrapper(activity, themeResId)
                PopupMenu(wrapper, binding.ivOptionArrow, Gravity.END, 0, themeResId)
            }
        }
        popupMenu?.inflate(menuResId)
        popupMenu?.setOnMenuItemClickListener {
            binding.tvValue.text = it.title
            it.isChecked = true
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