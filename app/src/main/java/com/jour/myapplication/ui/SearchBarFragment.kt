package com.jour.myapplication.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.jour.myapplication.base.mvvm.vm.EmptyViewModel
import com.jour.myapplication.common.ui.BaseFragment
import com.jour.myapplication.databinding.FragmentFirstBinding
import com.jour.myapplication.databinding.FragmentSearchBarBinding
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
