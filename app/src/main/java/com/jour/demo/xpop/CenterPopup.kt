package com.jour.demo.xpop

import android.content.Context
import android.text.method.ScrollingMovementMethod
import androidx.core.view.postDelayed
import com.jour.demo.databinding.PopupToastBinding
import com.jour.demo.R
import com.lxj.xpopup.core.CenterPopupView

class CenterPopup(context: Context, private val popTextStr: String = "") :
    CenterPopupView(context) {
    lateinit var mBinding: PopupToastBinding

    override fun getImplLayoutId(): Int {
        return R.layout.popup_toast
    }

    override fun onCreate() {
        super.onCreate()
        mBinding = PopupToastBinding.bind(popupImplView)
        mBinding.content.movementMethod = ScrollingMovementMethod()
        mBinding.content.text = popTextStr
        postDelayed(1000L) { dismiss() }
    }

}