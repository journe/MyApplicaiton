package com.jour.demo.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.jour.demo.base.mvvm.vm.EmptyViewModel
import com.jour.demo.common.ui.BaseFragment
import com.jour.demo.databinding.FragmentFirstBinding
import com.jour.demo.databinding.FragmentSearchBarBinding
import dagger.hilt.android.AndroidEntryPoint

class SearchBarFragment : BaseFragment<FragmentSearchBarBinding, EmptyViewModel>() {
	override val mViewModel: EmptyViewModel by viewModels()

	override fun FragmentSearchBarBinding.initView() {
	}

	override fun initObserve() {
	}

	override fun initRequestData() {
	}

}
