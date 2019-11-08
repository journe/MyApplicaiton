package com.example.lvchen.myapplication.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.example.lvchen.myapplication.R
import com.orhanobut.logger.Logger
import java.util.HashMap

class CountDownViewWrap2(
  context: Context?,
  attrs: AttributeSet?
) : FrameLayout(context, attrs) {

  companion object {
    const val OFFSET = -3f
    private var countdown: Countdown = Countdown(0f, 0, 0)
    private var showTipCount = 0
    private val showTipMap: MutableMap<String, Boolean> = HashMap()
  }

  constructor(context: Context?) : this(context, null)

  init {
    inflate(context, R.layout.view_count_down2, this)
  }

  private var lastX = 0
  private var lastY = 0

  private var totalOffset = 0

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent?): Boolean {
    val x = event?.x?.toInt() ?: 0
    val y = event?.y?.toInt() ?: 0
    Logger.d(x)
    when (event?.action) {
      MotionEvent.ACTION_DOWN -> {
        lastX = x
        lastY = y
      }
      MotionEvent.ACTION_MOVE -> {
        val offsetX = x - lastX
        val offsetY = y - lastY
        layout(left + offsetX, top + offsetY, right + offsetX, bottom + offsetY)
        layoutParams = (layoutParams as LayoutParams).apply {
          //          gravity = Gravity.RIGHT
//          com.orhanobut.logger.Logger.d("left$leftMargin" + "top " + topMargin)
//          val lm = leftMargin + offsetX
//          val tm = topMargin + offsetY
//          leftMargin = left + offsetX
//          topMargin = top + offsetY
//          rightMargin = right + offsetX
        }
        totalOffset += Math.abs(offsetX)
        totalOffset += Math.abs(offsetY)

      }
      MotionEvent.ACTION_UP -> {
        if (totalOffset < 5) {
        }
        totalOffset = 0
      }

    }
    return true
  }

  enum class State {
    PLAYING,
    PAUSE,
    STOP
  }

  private var state: State = State.STOP

  var topicId: String = ""

  //用户是否参与过阅读计时
  private var isReadingStatus = false




  // 本地计时,用于弹出提示
  private var localTime = 0


  private lateinit var animator: ValueAnimator

  private var animatorCount: Long = 0
  // 是否需要 8 秒暂停
  var needWait = false
  private var wait = 8

  private var lastDrawTime = 0f

  // 48 秒未切换时显示的文案
  var switchText: String? = null
  var tipsType: String? = null

  private var requestDelay = 6


  /**
   * 设置动画底部文本
   */
  fun setBottomText(text: String) {
//    tv.text = text
  }

  data class Countdown(
    var time: Float,
    var count: Long = 0,
    var expected: Int = 0
  )

  var onClickListener: OnClickListener? = null

  interface OnClickListener {
    fun onClick()
  }

}