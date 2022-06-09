package tech.echoing.base.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.lxj.xpopup.XPopup
import kotlinx.coroutines.*
import tech.echoing.base.lifecycle.GlobalDialogLifecycleObserver
import tech.echoing.base.util.ToastUtil
import java.lang.reflect.ParameterizedType

/**
 * Create by Crazy on 2020/12/7
 * EchoActivity : 基础Activity
 */
abstract class EchoActivity<T : EchoViewModel> : AppCompatActivity(), IBaseView {

    val exceptionContext = CommonCoroutinesException({ handelException(it) })

    lateinit var viewModel: T

    val globalDialogObserver = GlobalDialogLifecycleObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        lifecycle.addObserver(globalDialogObserver)
        val modelClass =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.last() as Class<T>
        viewModel = ViewModelProvider(this).get(modelClass)
        viewModel.viewEvent.observe(this, { if (!disposeSelfEvent(it)) it.triggerEvent(this) })
        afterView()
        viewModel.afterView()
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
        showToast(exception.message ?: "未知错误", ToastUtil.style.ERROR)
    }

    open fun showLoadingDialog(msg: String) {}

    open fun dismissLoadingDialog() {}

    fun showToast(toastMsg: String, type: ToastUtil.style = ToastUtil.style.NORMAL) {
        GlobalScope.launch(Dispatchers.Main.immediate) { ToastUtil.toast(toastMsg, type) }
    }

    fun showConfirmDialog(
        title: String,
        content: String,
        confirmAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        confirmText: String?,
        cancelText: String?
    ) = GlobalScope.launch(Dispatchers.Main.immediate) {
        XPopup.Builder(this@EchoActivity)
            .asConfirm(title, content, cancelText, confirmText,
                { confirmAction?.invoke() }, { cancelAction?.invoke() },
                cancelAction == null
            ).show()
    }

    fun immediateLaunch(task: () -> Unit = {}) = delayLaunch(0, task)

    fun delayLaunch(delayTime: Long = 3000, task: () -> Unit = {}) =
        lifecycleScope.launch(exceptionContext) {
            delay(delayTime)
            task()
        }

    fun loadingLaunch(
        loadingMsg: String? = null,
        exception: CommonCoroutinesException = exceptionContext,
        block: suspend CoroutineScope.() -> Unit
    ) = lifecycleScope.launch(exception) { loadingExecutionMethod(loadingMsg, block) }

    private suspend fun CoroutineScope.loadingExecutionMethod(
        loadingMsg: String?,
        block: suspend CoroutineScope.() -> Unit
    ) {
        showLoadingDialog(loadingMsg ?: "")
        block.invoke(this)
        dismissLoadingDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(globalDialogObserver)
    }
}