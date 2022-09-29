package com.jour.myapplication.common.ui

import android.util.Log
import androidx.viewbinding.ViewBinding
import com.jour.myapplication.base.mvvm.v.BaseFrameActivity
import com.jour.myapplication.base.mvvm.vm.BaseViewModel
import com.jour.myapplication.base.utils.ActivityStackManager
import com.jour.myapplication.base.utils.AndroidBugFixUtils
import com.jour.myapplication.base.utils.BarUtils

/**
 * Activity基类
 *
 * @author Qu Yunshuo
 * @since 8/27/20
 */
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : BaseFrameActivity<VB, VM>(),
    IUiView {

    /**
     * 设置状态栏
     * 子类需要自定义时重写该方法即可
     * @return Unit
     */
    override fun setStatusBar() {
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }

    override fun onResume() {
        super.onResume()
        Log.d("ActivityLifecycle", "ActivityStack: ${ActivityStackManager.activityStack}")
    }

    override fun onDestroy() {
        super.onDestroy()
        // 解决某些特定机型会触发的Android本身的Bug
        AndroidBugFixUtils().fixSoftInputLeaks(this)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }
}