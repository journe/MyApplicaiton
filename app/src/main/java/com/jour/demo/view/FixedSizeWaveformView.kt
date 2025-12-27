package com.jour.demo.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ScaleGestureDetector
import kotlin.math.min

class FixedSizeWaveformView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    // 波形数据（1024个整数，范围0-65535）
    private var waveformData: List<Int> = emptyList()

    // 波形画笔
    private val wavePaint = Paint().apply {
        color = Color.parseColor("#FF4081")
        strokeWidth = 2f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    // 背景画笔
    private val bgPaint = Paint().apply {
        color = Color.parseColor("#F5F5F5")
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    // 网格画笔
    private val gridPaint = Paint().apply {
        color = Color.parseColor("#E0E0E0")
        strokeWidth = 1f
        isAntiAlias = true
    }

    // 路径（绘制波形）
    private val path = Path()

    // 缩放/滚动相关
    private val scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1f // 初始缩放比例
    private val minScale = 0.1f // 最小缩放比例（适配屏幕）
    private val maxScale = 5f // 最大缩放比例（放大看细节）
    private var lastX = 0f
    private var lastY = 0f
    private var offsetX = 0f // X轴偏移（滚动）
    private var offsetY = 0f // Y轴偏移（滚动）

    // 固定尺寸：波形图原始宽高
    private val waveformWidth = 1024f
    private val waveformHeight = 65535f

    init {
        // 初始化缩放检测器
        scaleGestureDetector = ScaleGestureDetector(
            context,
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    // 计算缩放比例，限制范围
                    scaleFactor *= detector.scaleFactor
                    scaleFactor = scaleFactor.coerceIn(minScale, maxScale)
                    invalidate()
                    return true
                }
            })
    }

    /**
     * 设置波形数据并刷新
     */
    fun setWaveformData(data: List<Int>) {
        if (data.size != 1024) return
        waveformData = data
        // 初始化偏移：让波形居中显示
        offsetX = (width - waveformWidth * minScale) / 2f
        offsetY = (height - waveformHeight * minScale) / 2f
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 1. 绘制背景
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        if (waveformData.isEmpty()) return

        // 2. 保存画布状态（用于缩放/偏移后恢复）
        canvas.save()

        // 3. 应用缩放和偏移：让波形图在View中可缩放/滚动
        canvas.scale(scaleFactor, scaleFactor)
        canvas.translate(offsetX / scaleFactor, offsetY / scaleFactor)

        // 4. 绘制网格（可选，便于查看坐标）
        drawGrid(canvas)

        // 5. 构建波形路径（精准映射固定尺寸）
        path.reset()
        for (i in waveformData.indices) {
            // X轴：直接使用数据索引（0~1023），对应波形宽度1024
            val x = i.toFloat()
            // Y轴：直接使用数值（0~65535），对应波形高度65535
            // 反转Y轴：Canvas默认Y轴向下，反转后数值越大越靠上
            val y = waveformHeight - waveformData[i].toFloat()

            if (i == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        // 6. 绘制波形
        canvas.drawPath(path, wavePaint)

        // 7. 恢复画布状态
        canvas.restore()
    }

    /**
     * 绘制网格（可选）
     */
    private fun drawGrid(canvas: Canvas) {
        // 绘制竖线：每100个点画一条
        for (x in 0..waveformWidth.toInt() step 100) {
            canvas.drawLine(x.toFloat(), 0f, x.toFloat(), waveformHeight, gridPaint)
        }
        // 绘制横线：每5000个数值画一条
        for (y in 0..waveformHeight.toInt() step 5000) {
            canvas.drawLine(0f, y.toFloat(), waveformWidth, y.toFloat(), gridPaint)
        }
    }

    /**
     * 处理触摸事件：支持缩放和滚动
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
            }

            MotionEvent.ACTION_MOVE -> {
                if (!scaleGestureDetector.isInProgress) {
                    // 计算偏移量（缩放后需要修正偏移速度）
                    val dx = event.x - lastX
                    val dy = event.y - lastY
                    offsetX += dx
                    offsetY += dy
                    lastX = event.x
                    lastY = event.y
                    invalidate()
                }
            }
        }
        return true
    }

    /**
     * 测量View尺寸：设置默认大小，支持wrap_content
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val defaultWidth = 1024 // 默认宽度（对应波形宽度）
        val defaultHeight = 800 // 默认高度（屏幕可视高度）
        val width = measureDimension(defaultWidth, widthMeasureSpec)
        val height = measureDimension(defaultHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun measureDimension(defaultSize: Int, measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        return when (specMode) {
            MeasureSpec.EXACTLY -> specSize
            MeasureSpec.AT_MOST -> min(defaultSize, specSize)
            else -> defaultSize
        }
    }
}