package tech.echoing.base.util

import android.content.res.TypedArray
import com.orhanobut.logger.Logger
import java.text.DecimalFormat

/**
 * Create by Crazy on 2020/12/08
 */

/**
 * 保留两位，末尾不为0
 */
fun Float.keepTwoDecimalsNotZeroEnd(): String =
    this.decimalFormat("#.##")

/**
 * 保留两位，包含0
 */
fun Float.keepTwoDecimals(): String =
    this.decimalFormat("0.00")

fun Float.decimalFormat(formatStr: String): String =
    DecimalFormat(formatStr).format(this)

/**
 * 保留两位，末尾不为0
 */
fun Double.keepTwoDecimalsNotZeroEnd(): String =
    this.decimalFormat("#.##")

/**
 * 保留两位，包含0
 */
fun Double.keepTwoDecimals(): String =
    this.decimalFormat("0.00")

fun Double.decimalFormat(formatStr: String): String =
    DecimalFormat(formatStr).format(this)

fun <T> MutableList<T>.swap(first: Int, second: Int) {
    val tmp = this[first]
    this[first] = this[second]
    this[second] = tmp
}

fun TypedArray.analysisResourceOrColor(styleableId: Int): Int? {
    getResourceId(styleableId, -1).apply { if (this != -1) return resources.getColor(this) }
    getColor(styleableId, -1).apply { if (this != -1) return this }
    return null
}

fun Any.d() {
    Logger.d(this)
}