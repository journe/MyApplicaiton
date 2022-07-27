package com.example.jour.myapplication.utils

import android.content.Context
import android.view.WindowManager
import androidx.fragment.app.Fragment

/**
 * @Author: QuYunShuo
 * @Time: 2020/9/17
 * @Class: SizeUnitKtx
 * @Remark: 尺寸单位换算相关扩展属性
 */

/**
 * dp 转 px
 */
fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * px 转 dp
 */
fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * sp 转 px
 */
fun Context.sp2px(spValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (spValue * scale + 0.5f).toInt()
}

/**
 * px 转 sp
 */
fun Context.px2sp(pxValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * dp 转 px
 */
fun Fragment.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * px 转 dp
 */
fun Fragment.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * sp 转 px
 */
fun Fragment.sp2px(spValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (spValue * scale + 0.5f).toInt()
}

/**
 * px 转 sp
 */
fun Fragment.px2sp(pxValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (pxValue / scale + 0.5f).toInt()
}

fun Context.getWindowWidth() =
    (this.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.width

fun Context.getWindowHeight() =
    (this.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.height

fun Context.getWindowWidthDp() = this.px2dp(getWindowWidth().toFloat())

fun Context.getWindowHeightDp() = this.px2dp(getWindowHeight().toFloat())
