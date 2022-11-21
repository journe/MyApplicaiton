package com.jour.myapplication.ui.picture

import android.graphics.Bitmap
import android.graphics.Canvas
import com.blankj.utilcode.util.ImageUtils

fun List<Bitmap>.puzzleBitmap(): Bitmap {
    //缩放所有图片至统一宽度，以最长宽度为准
    val width = 1080
    val tempList = this.map {
        val scaleRate = width.toFloat() / it.width.toFloat()
        ImageUtils.scale(it, scaleRate, scaleRate)
    }

    // 计算画布高
    var height = 0
    for (bitmap in tempList) {
        height += bitmap.height
    }

    // 创建画布
    val resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(resultBitmap)

    // 拼接图片
    var top = 0f
    for (bitmap in tempList) {
        canvas.drawBitmap(bitmap, 0f, top, null)
        top += bitmap.height
    }

    return resultBitmap
}