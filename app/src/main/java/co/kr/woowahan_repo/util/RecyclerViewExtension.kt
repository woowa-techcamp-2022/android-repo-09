package co.kr.woowahan_repo.util

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.scrollToTop(animate: Boolean){
    this.post {
        this.adapter?.itemCount?.let {
            if(it == 0) return@let
            when(animate){
                true -> this.smoothScrollToPosition(0)
                else -> this.scrollToPosition(0)
            }
        }
    }
}