package co.kr.woowahan_repo.presentation

import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class PagingListener(
    private val pagingListener: ()->(Unit)
): RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if(!recyclerView.canScrollVertically(1)){ // direction 은 Vertically 기준으로 -1이 위쪽, 1이 아래쪽이다
            Timber.d("vertical end")
            recyclerView.adapter?.itemCount?.let {
                if(it != 0){
                    pagingListener()
                }
            }
        }
    }
}