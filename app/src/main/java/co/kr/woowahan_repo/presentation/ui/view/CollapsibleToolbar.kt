package co.kr.woowahan_repo.presentation.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import com.google.android.material.appbar.AppBarLayout

/**
 *  모션 레이아웃 학습
 *  https://developer.android.com/training/constraint-layout/motionlayout/examples?hl=ko
 *  https://medium.com/google-developers/introduction-to-motionlayout-part-iii-47cd64d51a5
 */

class CollapsibleToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): MotionLayout(context, attrs, defStyleAttr), AppBarLayout.OnOffsetChangedListener {

    /**
     * 이게 없으면 접히긴하는데, 모션이 안일어난다
     * 저 progress 에 따라서 모션이 이루어지는듯
     */
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        progress = -verticalOffset / appBarLayout?.totalScrollRange?.toFloat()!!
        Log.d("CollapsibleToolbar", "progress: $progress")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        (parent as? AppBarLayout)?.addOnOffsetChangedListener(this)
    }
}

/**
 * layout_scrollFlags
 * - 화면을 스크롤 할때, CollapsingToolbarLayout 을 얼마나 가리고 보일 것인지 정하는 속성
 * - 여러개를 넣을떄는 | 로 구분한다
 *  #https://jhshjs.tistory.com/24 (설명 잘되어있음)
 *  #https://jwsoft91.tistory.com/273 (gif도 포함되어있음)
 *
 *  테스트 환경
 *  scroll|enterAlways|snap|exitUntilCollapsed
 *  이 설정되어있는 상태
 *
 * 0. scroll
 *  : scroll 에 반응하라 ( 사실상 기본으로 넣고 시작하는 속성 )
 *  : 스크롤에 포함되어 이 뷰가 화면에서 사라질 수 있다
 *
 * 1.exitUntilCollapsed
 *  : 위로 스크롤: Toolbar 만 남기고 다 올림
 *  : 아래로 스크롤: 최상단까지 가면, CollapsingToolbarLayout 전체가 내려오기 시작
 *  => 제거해보니, 위로 스크롤시 Toolbar 가 스크롤에 포함되어 올라가 사라진다 ( enterAlways 의 속성이 적용안되다가 적용된다)
 *  => 아래로 스크롤시에는 따로 변경된 부분을 못찾앗다
 *
 * 2. enterAlways
 *  : 위로 스크롤: Toolbar 도 같이 다 올림
 *  : 아래로 스크롤: 스크롤 즉시, CollapsingToolBarLayout 전체가 내려오기 시작
 *  => 제거해보니, 위로 스크롤시 변경점 없음( exitUntilCollapsed 의 속성이 위로 스크롤할때는 우선이 된다고 이해해도 되나 )
 *  => 아래로 스크롤시, 즉시 CollapsingToolBarLayout 가 내려오기 시작(motion 을 시작하던것)하던 현상이 사라지고, 최상단에 도착해서 CollapsingToolBarLayout 이 내려오기 시작한다
 *
 * 3. enterAlwaysCollapsed
 *  : 위로 스크롤: Toolbar 도 같이 올림
 *  : 아래로 스크롤: 최상단까지 가면, CollapsingToolbarLayout 전체가 내려오기 시작
 *  -> 위로는 enterAlways, 아래로는 exitUntilCollapsed 를 적용한 느낌이네
 *
 *  4. snap
 *   : enterAlwaysCollapsed 와 동일한 효과 라고 한다
 *   => 제거했는데, 특별히 달라지는것은 없다
 *
 *   최종적으로는, search view motion 에는 exitUntilCollapsed 만 적용해도 될것같다
 *
 *
 */