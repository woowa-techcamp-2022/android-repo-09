package co.kr.woowahan_repo.domain

import java.text.SimpleDateFormat

class GithubApiDateFormat private constructor(){
    companion object {
        private var instance: GithubApiDateFormat? = null
        fun getInstance(): GithubApiDateFormat {
            if(instance == null)
                instance = GithubApiDateFormat()
            return instance!!
        }
    }

    private val githubDateFormatString = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private val formatter = SimpleDateFormat(githubDateFormatString)

    fun getSimpleDateFormat(): SimpleDateFormat {
        return formatter
    }
}