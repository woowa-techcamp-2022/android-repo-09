package co.kr.woowahan_repo.domain

import java.text.SimpleDateFormat
import java.util.*

class GithubApiDateFormat {
    private val githubDateFormatString = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private val formatter = SimpleDateFormat(githubDateFormatString).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun getSimpleDateFormat(): SimpleDateFormat {
        return formatter
    }
}