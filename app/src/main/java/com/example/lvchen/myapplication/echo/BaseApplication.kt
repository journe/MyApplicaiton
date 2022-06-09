package tech.echoing.base.base

import android.R
import android.app.Application
import android.content.Context
import android.graphics.Color
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import tech.echoing.base.third.util.RouterUtil
import tech.echoing.base.util.ToastUtil


/**
 * Create by Crazy on 2020/12/8
 * BaseApplication : 只包含最基础的Application
 */
open class BaseApplication : Application() {

    val applicationScope =
        CoroutineScope(Dispatchers.Main.immediate + SupervisorJob() + commonCoroutinesException)

    companion object {
        lateinit var INSTANCE: BaseApplication
        private val commonCoroutinesException = CommonCoroutinesException({
            ToastUtil.error(it.message ?: "未知错误").show()
        })

        fun immediateLaunch(
            exception: CommonCoroutinesException = commonCoroutinesException,
            block: suspend CoroutineScope.() -> Unit
        ) = INSTANCE.applicationScope.launch(context = exception, block = block)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        ToastUtil.init(this)
        MMKV.initialize(this)
        RouterUtil.init(this)
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColors(Color.parseColor("#05BEA9")) //全局设置主题颜色
            MaterialHeader(context)
//            ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }
}