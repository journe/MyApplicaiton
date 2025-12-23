package com.jour.demo.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class WaveformView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    // 波形数据（1024个整数）
    private var waveformData: List<Int> = emptyList()

    // 画笔：绘制波形线
    private val wavePaint = Paint().apply {
        color = Color.parseColor("#FF4081") // 波形颜色
        strokeWidth = 2f // 线宽
        style = Paint.Style.STROKE
        isAntiAlias = true // 抗锯齿
    }

    // 画笔：绘制背景
    private val bgPaint = Paint().apply {
        color = Color.parseColor("#F5F5F5")
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    // 波形的最大/最小值（用于坐标映射）
    private var maxValue = 0
    private var minValue = 0

    // 坐标点数组（优化绘制性能，避免重复计算）
    private val path = Path()

    /**
     * 设置波形数据并刷新视图
     */
    fun setWaveformData(data: List<Int>) {
        if (data.size != 1024) return // 确保数据长度为1024
        waveformData = data
        // 计算数据的最大/最小值，用于Y轴映射
        maxValue = data.maxOrNull() ?: 0
        minValue = data.minOrNull() ?: 0
        invalidate() // 触发重绘
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 1. 绘制背景
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        if (waveformData.isEmpty() || maxValue == minValue) return

        // 2. 计算坐标映射参数
        val totalPoints = waveformData.size // 1024个点
        val xStep = width.toFloat() / (totalPoints - 1) // 每个点的X轴步长
        val valueRange = maxValue - minValue // 数据值的范围
        val yScale = height.toFloat() / valueRange // Y轴缩放比例（将数据值映射到View高度）

        // 3. 构建波形路径
        path.reset()
        for (i in waveformData.indices) {
            // X坐标：从左到右均匀分布
            val x = i * xStep
            // Y坐标：将数据值映射到View的Y轴（反转Y轴，因为Canvas的Y轴向下为正）
            val normalizedValue = waveformData[i] - minValue
            val y = height - (normalizedValue * yScale) // 反转后，数据值越大，Y坐标越靠上

            if (i == 0) {
                path.moveTo(x, y) // 第一个点，移动到该位置
            } else {
                path.lineTo(x, y) // 后续点，绘制直线连接
            }
        }

        // 4. 绘制波形路径
        canvas.drawPath(path, wavePaint)
    }
}