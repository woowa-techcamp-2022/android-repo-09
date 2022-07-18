package co.kr.woowahan_repo.util

import android.graphics.Color
import okhttp3.internal.toHexString
import timber.log.Timber

object ColorUtil {
    private val colorMap = mutableMapOf<String, String>()

    fun getColor(seed: String): Int = getLanguageColorFromMap(seed)

    private fun getLanguageColorFromMap(seed: String): Int{
        if(colorMap[seed] != null)
            return Color.parseColor("#${colorMap[seed]!!}")
        val color = (1111..9999).random()
        val header = (0x0F..0xFF).random().toHexString()
        val colorValue = when((0..2).random()){
            0 -> "${header}${color}"
            1 -> "${String.format("%02d", color/100)}${header}${String.format("%02d", color%100)}"
            else -> "${color}${header}"
        }
        colorMap[seed] = colorValue
        return Color.parseColor("#${colorMap[seed]!!}")
    }

    private fun getLanguageColorFromHash(seed: String): Int{
        val hash: Int = seed.hashCode()
        val r = hash and 0xFF0000 shr 16 //(hash & 0xFF0000) >> 16
        val g = hash and 0x00FF00 shr 8
        val b = hash and 0x0000FF
        Timber.d("getLanguageColor[$seed] => [$hash] => r[$r] g[$g] b[$b]")
        val color = Color.valueOf(r.toFloat(), g.toFloat(), b.toFloat())
        return color.toArgb()
    }

}