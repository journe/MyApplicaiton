package tech.echoing.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import tech.echoing.base.util.ToastUtil
import java.lang.reflect.ParameterizedType

/**
 * Create by Crazy on 2020/12/7
 * EchoFragment : 基础Fragment
 */
abstract class EchoFragment<T : EchoViewModel> : Fragment(), IBaseView {

    var isLoaded = false
    val exceptionContext = CommonCoroutinesException({ handelException(it) })
    val mainScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob() + exceptionContext)
    lateinit var viewModel: T
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(getLayoutId(), container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val modelClass =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        viewModel = ViewModelProvider(this).get(modelClass)
        viewModel.viewEvent.observe(
            this.viewLifecycleOwner,
            { if (!disposeSelfEvent(it)) it.triggerEvent(this.activity as EchoActivity<*>) })
        afterView()
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded = true
        } else if (!isHidden) {
            nonFirstResume()
        }
    }

    abstract fun lazyInit()

    open fun nonFirstResume() {}

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
        mainScope.cancel()
    }

    /**
     * 处理特有ViewEvent,如果交由基础类处理就返回false 否则返回true
     */
    @CallSuper
    open fun disposeSelfEvent(viewEvent: ViewEvent): Boolean =
        if (viewEvent is SimpleViewEvent) {
            invokeSimpleEvent(viewEvent.code)
            true
        } else false

    /**
     * 执行通用ViewEvent
     * 使用方法：覆写
     */
    open fun invokeSimpleEvent(code: String) {}

    open fun handelException(exception: Throwable) {
        exception.printStackTrace()
        showToast(exception.message ?: "未知错误")
    }

    private fun showToast(toastMsg: String) {
        GlobalScope.launch(Dispatchers.Main.immediate) { ToastUtil.normal(toastMsg).show() }
    }

    fun immediateLaunch(task: () -> Unit = {}) = delayLaunch(0, task)

    fun delayLaunch(delayTime: Long = 3000, task: () -> Unit = {}) =
        lifecycleScope.launch(exceptionContext) {
            delay(delayTime)
            task()
        }
}