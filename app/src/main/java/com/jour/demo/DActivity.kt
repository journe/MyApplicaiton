package com.jour.demo

import com.jour.demo.base.mvvm.vm.EmptyViewModel
import com.jour.demo.common.ui.BaseActivity
import com.jour.demo.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DActivity : BaseActivity<FragmentFirstBinding, EmptyViewModel>() {

	override fun FragmentFirstBinding.initView() {
	}

	override fun initObserve() {
	}

	override fun initRequestData() {
	}

}
