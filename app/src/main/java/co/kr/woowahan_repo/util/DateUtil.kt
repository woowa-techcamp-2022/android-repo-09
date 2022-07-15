package co.kr.woowahan_repo.util

import android.icu.util.Calendar
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

object DateUtil {
    /**
     * month 값의 근삿값 결과를 얻으려면 day 값을 30.417(으)로 나눕니다.
     */
    fun getLastUpdateIntervalDateString(lastUpdateAt: String): String{
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val lastUpdateDate: Date = dateFormat.parse(lastUpdateAt)!!
            Timber.tag("processLastUpdateDate").d(lastUpdateDate.toString())
            val now = Calendar.getInstance()
            val lastUpdateDateMill = now.timeInMillis - lastUpdateDate.time
            val day = TimeUnit.MILLISECONDS.toDays(lastUpdateDateMill)
            Timber.tag("processLastUpdateDate").d("raw day => $day")
            val year = day/365
            return when {
                day <1 -> {
                    val hour = TimeUnit.MILLISECONDS.toHours(lastUpdateDateMill)
                    if(hour>0)
                        "${hour}시간전"
                    else{
                        val min = TimeUnit.MILLISECONDS.toMinutes(lastUpdateDateMill)
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
        }catch (e: Exception){
            e.printStackTrace()
            return ""
        }
    }
}