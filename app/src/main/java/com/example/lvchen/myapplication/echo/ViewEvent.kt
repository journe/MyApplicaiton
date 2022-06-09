package tech.echoing.base.base

import android.net.Uri
import com.alibaba.android.arouter.facade.Postcard
import tech.echoing.base.R
import tech.echoing.base.third.util.RouterUtil
import tech.echoing.base.util.ToastUtil

/**
 * Create by Crazy on 2020/12/7
 * ViewEvent : ViewModel与Activity交互接口
 */
interface ViewEvent {
    fun triggerEvent(activity: EchoActivity<*>)
}

open class BaseViewEvent : ViewEvent {
    override fun triggerEvent(activity: EchoActivity<*>) {}
}

class SimpleViewEvent(val code: String) : ViewEvent {
    override fun triggerEvent(activity: EchoActivity<*>) {
        activity.invokeSimpleEvent(code)
    }
}

class ViewEventOfFinish : ViewEvent {
    override fun triggerEvent(activity: EchoActivity<*>) {
        activity.finish()
    }
}

class ViewEventOfShowLoading(val msg: String? = null, val msgId: Int? = null) : ViewEvent {
    override fun triggerEvent(activity: EchoActivity<*>) {
        when {
            msg != null -> activity.showLoadingDialog(msg)
            msgId != null -> activity.showLoadingDialog(activity.getString(msgId))
            else -> activity.showLoadingDialog("")
        }
    }
}

class ViewEventOfDismissLoading : ViewEvent {
    override fun triggerEvent(activity: EchoActivity<*>) {
        activity.dismissLoadingDialog()
    }
}

/**
 * 展示Toast
 */
class ViewEventOfToast : ViewEvent {

    var msg: String? = null
    var msgId: Int? = null
    var type = ToastUtil.style.NORMAL

    constructor(msg: String, type: ToastUtil.style = ToastUtil.style.NORMAL) {
        this.msg = msg
        this.type = type
    }

    constructor(msgId: Int, type: ToastUtil.style = ToastUtil.style.NORMAL) {
        this.msgId = msgId
        this.type = type
    }

    override fun triggerEvent(activity: EchoActivity<*>) {
        val trueMsg = msg ?: activity.getString(msgId ?: R.string.app_name)
        activity.showToast(trueMsg, type)
    }

}

class ViewEventOfShowConfirmDialog(
    private val title: String,
    private val content: String,
    private val confirmAction: (() -> Unit)? = null,
    private val cancelAction: (() -> Unit)? = null,
    private val confirmText: String?,
    private val cancelText: String?
) : ViewEvent {

    override fun triggerEvent(activity: EchoActivity<*>) {
        activity.showConfirmDialog(
            title,
            content,
            confirmAction,
            cancelAction,
            confirmText,
            cancelText
        )
    }

}

/**
 * 开启Activity
 */
class ViewEventOfStartActivity(
    val path: String? = null,
    val uri: Uri? = null,
    val postcard: Postcard? = null
) : ViewEvent {
    override fun triggerEvent(activity: EchoActivity<*>) {
        path?.run {
            RouterUtil.navigation(this, activity)
            return
        }
        postcard?.run {
            RouterUtil.navigation(this, activity)
            return
        }
        uri?.run {
            RouterUtil.navigation(this, activity)
            return
        }
        ToastUtil.error("不能开启activity，没有任何开启参数").show()
    }
}

