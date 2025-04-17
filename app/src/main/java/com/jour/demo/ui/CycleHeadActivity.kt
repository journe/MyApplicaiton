package com.jour.demo.ui

import android.view.ViewGroup
import com.jour.demo.base.mvvm.vm.EmptyViewModel
import com.jour.demo.common.ui.BaseActivity
import com.jour.demo.databinding.ActivityCycleHeadBinding

class CycleHeadActivity : BaseActivity<ActivityCycleHeadBinding, EmptyViewModel>() {

	override fun ActivityCycleHeadBinding.initView() {
//		RenderEffectBlur(this@CycleHeadActivity)
		blurView.setupWith(window.decorView.findViewById<ViewGroup>(android.R.id.content))
			.setFrameClearDrawable(window.decorView.background)
			// Optionally pass RenderEffectBlur or RenderScriptBlur as the second parameter
			.setBlurRadius(20f)
	}

	override fun initObserve() {
	}

	override fun initRequestData() {
	}
}