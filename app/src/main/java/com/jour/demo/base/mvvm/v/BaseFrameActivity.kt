package com.jour.demo.base.mvvm.v

import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.jour.demo.base.mvvm.vm.BaseViewModel
import com.jour.demo.base.utils.*
import com.jour.demo.base.utils.network.AutoRegisterNetListener
import com.jour.demo.base.utils.network.NetworkStateChangeListener
import com.jour.demo.base.utils.network.NetworkTypeEnum
import java.lang.reflect.ParameterizedType

/**
 * Activity基类
 *
 * @author Qu Yunshuo
 * @since 8/27/20
 */
abstract class BaseFrameActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity(),
	FrameView<VB>, NetworkStateChangeListener {

	protected val mBinding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
		BindingReflex.reflexViewBinding(javaClass, layoutInflater)
	}

	protected val mViewModel: VM by lazy(mode = LazyThreadSafetyMode.NONE) {
		BindingReflex.reflexViewModel(javaClass, this)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(mBinding.root)
		// ARouter 依赖注入
//        ARouter.getInstance().inject(this)
		// 注册EventBus
//        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.register(this)

//		createViewModel()
		setStatusBar()
		mBinding.initView()
		initNetworkListener()
		initObserve()
		initRequestData()
	}

	private fun createViewModel() {
		//随着activity销毁而销毁viewmodel
//		val clazzVM: Class<VM> = TUtil.getClazz<Class<VM>>(this, 1)
//		viewModel = ViewModelProvider.NewInstanceFactory().create(clazzVM)
//		lifecycle.addObserver(mViewModel)
	}

	/**
	 * 初始化网络状态监听
	 * @return Unit
	 */
	private fun initNetworkListener() {
		lifecycle.addObserver(AutoRegisterNetListener(this))
	}

	/**
	 * 设置状态栏
	 * 子类需要自定义时重写该方法即可
	 * @return Unit
	 */
	open fun setStatusBar() {}

	/**
	 * 网络类型更改回调
	 * @param type Int 网络类型
	 * @return Unit
	 */
	override fun networkTypeChange(type: NetworkTypeEnum) {}

	/**
	 * 网络连接状态更改回调
	 * @param isConnected Boolean 是否已连接
	 * @return Unit
	 */
	override fun networkConnectChange(isConnected: Boolean) {
		toast(if (isConnected) "网络已连接" else "网络已断开")
	}

	override fun onDestroy() {
//        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.unRegister(
//            this
//        )
		super.onDestroy()
	}

	override fun getResources(): Resources {
		// 主要是为了解决 AndroidAutoSize 在横屏切换时导致适配失效的问题
		// 但是 AutoSizeCompat.autoConvertDensity() 对线程做了判断 导致Coil等图片加载框架在子线程访问的时候会异常
		// 所以在这里加了线程的判断 如果是非主线程 就取消单独的适配
//        if (Looper.myLooper() == Looper.getMainLooper()) {
//            AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()))
//        }
		return super.getResources()
	}
}