package com.jour.demo.xpop

import android.content.Context
import com.jour.demo.R
import com.jour.demo.databinding.PopupLoadingBinding
import com.lxj.xpopup.core.CenterPopupView

class LoadingPopup(context: Context) :
    CenterPopupView(context) {
    lateinit var mBinding: PopupLoadingBinding

    override fun getImplLayoutId(): Int {
        return R.layout.popup_loading
    }

    override fun onCreate() {
        super.onCreate()
        mBinding = PopupLoadingBinding.bind(popupImplView)
    }

}