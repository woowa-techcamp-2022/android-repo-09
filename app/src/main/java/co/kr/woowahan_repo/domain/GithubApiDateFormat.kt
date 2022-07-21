package co.kr.woowahan_repo.domain

import java.text.SimpleDateFormat

class GithubApiDateFormat {
    private val githubDateFormatString = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private val formatter = SimpleDateFormat(githubDateFormatString)

    fun getSimpleDateFormat(): SimpleDateFormat {
        return formatter
    }
}