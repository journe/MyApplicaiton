package com.example.lvchen.myapplication.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.lvchen.myapplication.R
import kotlinx.android.synthetic.main.layout_expand_collpase_text_view.view.*

class ExpandCollapsedTextView : LinearLayoutCompat {

    private lateinit var mTvTitleText: FolderTextView

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

    private fun init(context: Context) {
        // 初始化布局文件
        LayoutInflater.from(context).inflate(
            R.layout.layout_expand_collpase_text_view,
            this, true
        )

        // 获取引用
        mTvTitleText = findViewById(R.id.folder_text)
        val expandBtn = findViewById<TextView>(R.id.expand_text)
        expandBtn.setOnClickListener {
            mTvTitleText.performClick()
        }
//        mEdTexts.add(findViewById(R.id.ed_1))
//        mEdTexts.add(findViewById(R.id.ed_2))
//        mEdTexts.add(findViewById(R.id.ed_3))
//        mEdTexts.add(findViewById(R.id.ed_4))
    }

    fun setText(text: String) {
        mTvTitleText.text = text
    }

    interface onKeyFinishListener {
        fun onKeyRight()
        fun onKeyError()
    }
}