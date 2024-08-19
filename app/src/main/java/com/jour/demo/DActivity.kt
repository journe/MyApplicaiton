package com.jour.demo

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.jour.demo.base.mvvm.vm.EmptyViewModel
import com.jour.demo.common.ui.BaseActivity
import com.jour.demo.common.ui.BaseFragment
import com.jour.demo.databinding.ActivityArouterBinding
import com.jour.demo.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DActivity : BaseActivity<FragmentFirstBinding, EmptyViewModel>() {
	override val mViewModel: EmptyViewModel by viewModels()

	override fun FragmentFirstBinding.initView() {
	}

	override fun initObserve() {
	}

	override fun initRequestData() {
	}

}
