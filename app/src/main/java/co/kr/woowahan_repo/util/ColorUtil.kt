package co.kr.woowahan_repo.util

import android.graphics.Color
import androidx.core.graphics.toColor
import okhttp3.internal.toHexString
import timber.log.Timber
import kotlin.math.abs

object ColorUtil {
    private val colorMap = mutableMapOf<String, Int>()

    fun getColor(seed: String): Int = getColorFromMap(seed)

    private fun getColorFromMap(key: String): Int{
        if(colorMap[key] != null)
            return colorMap[key]!!
        colorMap[key] = getLanguageColorFromHash2(key)//makeRandomColor()
        Timber.d("color from map key[$key] [${colorMap[key]}]")
        return colorMap[key]!!
    }

    private fun makeRandomColor(): Int {
        val color = (1111..9999).random()
        val header = (0x0F..0xFF).random().toHexString()
        val colorValue = when((0..2).random()){
            0 -> "#${header}${color}"
            1 -> "#${String.format("%02d", color/100)}${header}${String.format("%02d", color%100)}"
            else -> "#${color}${header}"
        }
        Timber.d("make random color[$colorValue]")
        return Color.parseColor(colorValue)
    }

    /**
     * 나누고, 곱하는 값들은 별 의미 없는 값들
     * rgb 값 간의 차이를 두고 싶어서 부여한 값 + 언어별 다른 값을 부여하기 위함
     * => 그냥 hash 로만 하면 가끔 같은 값을 가진 언어가 나온다
     * => 시간이 좀 된다면 중복을 없앨 수 있는 코드를 짜야겠다. 지금 것도 테스트 중엔 나오지 않았지만,
     * 예상치 못하게 중복이 나온다 해도 할 말은 없는 거같다
     */
    private fun getLanguageColorFromHash2(seed: String): Int{
        val hash: Int = seed.hashCode()
        val r = abs(hash/7).toLong()*16%256
        val g = abs(hash/7).toLong()*8%256
        val b = abs(hash/7)%256
        val color = Color.rgb(r.toInt(), g.toInt(), b) //Color.valueOf(r.toFloat(), g.toFloat(), b.toFloat())
        Timber.d("getLanguageColor2[$seed] => [$hash] => r[$r] g[$g] b[$b]")
        return color
    }

    private fun getLanguageColorFromHash(seed: String): Int{
        val hash: Int = seed.hashCode()
        val r = hash and 0xFF0000 shr 16 //(hash & 0xFF0000) >> 16
        val g = hash and 0x00FF00 shr 8
        val b = hash and 0x0000FF
        val color = Color.rgb(r.toFloat(), g.toFloat(), b.toFloat())
        Timber.d("getLanguageColor[$seed] => [$hash] => r[$r] g[$g] b[$b]")
        return color
    }

}