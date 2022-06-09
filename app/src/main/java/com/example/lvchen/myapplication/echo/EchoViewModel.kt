package tech.echoing.base.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import tech.echoing.base.util.ToastUtil

/**
 * Create by Crazy on 2020/12/7
 * EchoViewModel : 基础ViewModel
 */
abstract class EchoViewModel : ViewModel() {

    /**
     * 向Activity发送数据方式
     */
    val viewEvent = MutableLiveData<ViewEvent>()
    val exceptionContext = BaseCoroutinesException(viewEvent, { handelException(it) })

    open fun handelException(it: Throwable) {
        it.printStackTrace()
//        showToast(it.message.toString())
    }

    abstract fun afterView()

    fun immediateLaunch(
        exception: BaseCoroutinesException = exceptionContext,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(exception) { block() }

    fun loadingLaunch(
        loadingMsg: String? = null,
        exception: BaseCoroutinesException = exceptionContext,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(exception) { loadingExecutionMethod(loadingMsg, block) }

    fun delayLaunch(delayTime: Long = 3000, task: () -> Unit = {}) {
        viewModelScope.launch {
            delay(delayTime)
            task.invoke()
        }
    }

    private suspend fun CoroutineScope.loadingExecutionMethod(
        loadingMsg: String?,
        block: suspend CoroutineScope.() -> Unit
    ) {
        postEvent(ViewEventOfShowLoading(loadingMsg))
        block.invoke(this)
        postEvent(ViewEventOfDismissLoading())
    }

    fun postEvent(event: ViewEvent) {
        viewEvent.value = event
    }

    fun showToast(msgId: Int, type: ToastUtil.style = ToastUtil.style.NORMAL) {
        postEvent(ViewEventOfToast(msgId, type))
    }

    fun showToast(msg: String, type: ToastUtil.style = ToastUtil.style.NORMAL) {
        postEvent(ViewEventOfToast(msg, type))
    }

    fun startActivity(path: String) {
        postEvent(ViewEventOfStartActivity(path))
    }

    fun finish() {
        viewEvent.value = ViewEventOfFinish()
    }
}