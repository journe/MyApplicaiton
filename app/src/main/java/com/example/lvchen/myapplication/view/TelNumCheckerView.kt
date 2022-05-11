package com.example.lvchen.myapplication.view

import android.content.Context
import com.example.lvchen.myapplication.utils.KeyboardUtils.Companion.showSoftInput
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import android.widget.EditText
import android.view.LayoutInflater
import com.example.lvchen.myapplication.R
import com.example.lvchen.myapplication.utils.KeyboardUtils
import android.text.Editable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.orhanobut.logger.Logger

class TelNumCheckerView : ConstraintLayout {
    private var mTvTitleText: TextView? = null
    private val mEdTexts = mutableListOf<EditText>()

    // 当前所在光标所在EditText索引
    private var mIdx = 0

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        // 初始化布局文件
        LayoutInflater.from(context).inflate(
            R.layout.tel_num_unbind_check,
            this, true
        )

        // 获取引用
        mTvTitleText = findViewById(R.id.text_number)
        mEdTexts.add(findViewById(R.id.ed_1))
        mEdTexts.add(findViewById(R.id.ed_2))
        mEdTexts.add(findViewById(R.id.ed_3))
        mEdTexts.add(findViewById(R.id.ed_4))
        initEdits()
    }

    private fun initEdits() {
        for (i in mEdTexts.indices) {
            mEdTexts[i].apply {
                setOnFocusChangeListener { p0, isFocus ->
                    if (isFocus) {
                        mIdx = i
                    }
                }
                setOnKeyListener(mKeyListener)
            }
        }
        // 让第一个EditText获取焦点
        mEdTexts[0].apply {
            requestFocus()
        }

    }

    private val mKeyListener: OnKeyListener = object : OnKeyListener {

        // 删除时，用于记录光标的移动方向
        private val DIRECTION_LEFT = -1
        private val DIRECTION_RIGHT = 1
        override fun onKey(view: View, keyCode: Int, keyEvent: KeyEvent): Boolean {
            if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    val textLength = mEdTexts[mIdx].text.toString().length

                    // 在删除前已经没有内容，需要退格
                    if (textLength == 0) {
                        // 如果此时光标就在第一格，直接拦截，放弃此次点击
                        if (mIdx == 0) {
                            return true
                        } else {
                            mIdx -= 1
                            mEdTexts[mIdx].setText("")
                            // 左移光标
                            moveCursor(mIdx, DIRECTION_LEFT)
                        }
                    } else {
                        // 仍然有内容，走正常逻辑
                        return false
                    }
                } else {
                    // 已经到了尾格，不需要移动光标，放弃不处理
                    if (mIdx + 1 == mEdTexts.size) {
                        return false
                    }
                    // 不是尾格，需要移动光标
                    mIdx += 1
                    // 右移光标
                    moveCursor(mIdx, DIRECTION_RIGHT)
                    return false
                }
            }
            return false
        }

        private fun moveCursor(toIdx: Int, direction: Int) {
            // 解注册上一个editText
//            mEdTexts[toIdx - direction].setOnKeyListener(null)
//            mEdTexts[toIdx - direction].isEnabled = false
            // 让其他EditText不可点击，防止光标索引错乱
            disEnableOther(toIdx)
            mEdTexts[toIdx].requestFocus()
//            mEdTexts[toIdx].setOnKeyListener(this)
        }

        private fun disEnableOther(toIdx: Int) {
//            mEdTexts[toIdx].isEnabled = true
//            for (i in mEdTexts.indices) {
//                mEdTexts[toIdx].isFocusable = i==toIdx
//            }
        }
    }

    interface onKeyFinishListener {
        fun onKeyRight()
        fun onKeyError()
    }
}