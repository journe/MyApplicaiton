package com.jour.myapplication.ui.editview

import android.graphics.PixelFormat
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.SizeUtils
import com.jour.myapplication.base.BaseActivity
import com.jour.myapplication.databinding.ActivityEditTextBinding
import com.jour.myapplication.databinding.LayoutFloatViewBinding
import com.jour.myapplication.utils.animateY
import com.jour.myapplication.utils.gone
import com.jour.myapplication.utils.popup
import com.jour.myapplication.utils.visible
import com.orhanobut.logger.Logger

class EditTextActivity : BaseActivity() {
    lateinit var binding: ActivityEditTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val floatViewBinding = LayoutFloatViewBinding.inflate(layoutInflater)

        var layoutParam = WindowManager.LayoutParams().apply {
            //设置大小 自适应
            width = WRAP_CONTENT
            height = WRAP_CONTENT
            format = PixelFormat.TRANSLUCENT
            gravity = Gravity.END
            flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        }
        windowManager.addView(floatViewBinding.root, layoutParam)


        floatViewBinding.fabKeyboard.setOnClickListener {
            KeyboardUtils.hideSoftInput(this)
        }
        var firstShow = true
        KeyboardUtils.registerSoftInputChangedListener(this@EditTextActivity) { keyheight ->
            if (keyheight == 0) {
                binding.fabKeyboard.popup()
                binding.fabKeyboard.gone()
                floatViewBinding.fabKeyboard.gone()
            } else {
                floatViewBinding.fabKeyboard.visible()
                if (firstShow) {
                    firstShow = false
                    val location = IntArray(2)
                    floatViewBinding.root.getLocationOnScreen(location)
                    Logger.d(location)
                    val x = location[0]
                    val y = location[1]
                    Logger.d(keyheight)
                    layoutParam.y = y - keyheight - SizeUtils.dp2px(52f)
                    windowManager.updateViewLayout(floatViewBinding.root, layoutParam)
                }

                binding.fabKeyboard.animateY(0 - SizeUtils.px2dp(keyheight.toFloat()).toFloat())
                binding.fabKeyboard.visible()

            }
        }


    }
}