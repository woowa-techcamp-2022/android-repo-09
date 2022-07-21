package co.kr.woowahan_repo.util

import android.icu.util.Calendar
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

object DateUtil {

    private val githubDateFormatString = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    fun getGithubDateInterval(lastDate: String): String{
        Timber.tag("processLastUpdateDate raw").d(lastDate)
        return try {
            val lastUpdateDate = parseDate(lastDate, githubDateFormatString)!!
            Timber.tag("processLastUpdateDate").d(lastUpdateDate.toString())
            getDistanceString(lastUpdateDate)
        }catch (e: Exception){
            e.printStackTrace()
            ""
        }
    }

    private fun parseDate(dateString: String, dateFormatString: String): Date? {
        val dateFormat = SimpleDateFormat(dateFormatString)
        return dateFormat.parse(dateString)
    }

    /**
     * month 값의 근삿값 결과를 얻으려면 day 값을 30.417(으)로 나눕니다.
     */
    private fun getDistanceString(date: Date): String {
        val now = Calendar.getInstance()
        val lastDateMill = now.timeInMillis - date.time
        val day = TimeUnit.MILLISECONDS.toDays(lastDateMill)
        Timber.tag("processLastUpdateDate").d("raw day => $day")
        val year = day/365
        return when {
            day <1 -> {
                val hour = TimeUnit.MILLISECONDS.toHours(lastDateMill)
                if(hour>0)
                    "${hour}시간전"
                else{
                    val min = TimeUnit.MILLISECONDS.toMinutes(lastDateMill)
                    "${min}분전"
                }
            }
            year>0 -> {
                Timber.tag("processLastUpdateDate").d("${year}년전")
                "${year}년전"
            }
            else -> {
                val month = (day / 30.417).roundToInt()
                when {
                    month>0 -> {
                        Timber.tag("processLastUpdateDate").d("${month}달전")
                        "${month}달전"
                    }
                    else -> {
                        Timber.tag("processLastUpdateDate").d("${day}일전")
                        "${day}일전"
                    }
                }
            }
        }
    }
}