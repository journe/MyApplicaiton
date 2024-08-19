package com.jour.demo.common.ui

import androidx.viewbinding.ViewBinding
import com.jour.demo.base.mvvm.v.BaseFrameFragment
import com.jour.demo.base.mvvm.vm.BaseViewModel

/**
 * Fragment基类
 *
 * @author Qu Yunshuo
 * @since 8/27/20
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFrameFragment<VB, VM>(),
    IUiView {
    override fun showLoading() {
    }

    override fun dismissLoading() {
    }
}