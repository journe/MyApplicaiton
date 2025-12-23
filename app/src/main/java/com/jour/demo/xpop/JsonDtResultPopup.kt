package com.jour.demo.xpop

import android.content.Context
import com.jour.demo.R
import com.jour.demo.databinding.PopupJsonDtResultBinding
import com.lxj.xpopup.core.CenterPopupView

class JsonDtResultPopup(context: Context, private val waveformData: List<Int> = emptyList()) :
    CenterPopupView(context) {
    lateinit var mBinding: PopupJsonDtResultBinding

    override fun getImplLayoutId(): Int {
        return R.layout.popup_json_dt_result
    }

    override fun onCreate() {
        super.onCreate()
        mBinding = PopupJsonDtResultBinding.bind(popupImplView)
        mBinding.waveformView.setWaveformData(waveformData)
    }

}