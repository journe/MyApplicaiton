package com.jour.myapplication

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.jour.myapplication.base.mvvm.vm.EmptyViewModel
import com.jour.myapplication.common.ui.BaseActivity
import com.jour.myapplication.common.ui.BaseFragment
import com.jour.myapplication.databinding.ActivityArouterBinding
import com.jour.myapplication.databinding.FragmentFirstBinding
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
