package com.jour.demo.xpop

import android.content.Context
import android.text.method.ScrollingMovementMethod
import com.jour.demo.R
import com.jour.demo.databinding.PopupJsonResultBinding
import com.lxj.xpopup.core.CenterPopupView

class JsonResultPopup(context: Context, private val popTextStr: String = "") :
    CenterPopupView(context) {
    lateinit var mBinding: PopupJsonResultBinding

    override fun getImplLayoutId(): Int {
        return R.layout.popup_json_result
    }

    override fun onCreate() {
        super.onCreate()
        mBinding = PopupJsonResultBinding.bind(popupImplView)
        mBinding.content.movementMethod = ScrollingMovementMethod()
        mBinding.content.text = popTextStr
    }

}