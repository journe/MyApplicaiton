package com.example.lvchen.myapplication.echo

import android.app.Activity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import tech.echoing.base.util.GlobalDialogUtil

/**
 * Create by Crazy on 2020/12/8
 * LifecycleObserverCollection : 所有LifeObserver 监听集合
 */

/**
 * 全局弹窗生命周期监听
 */
class GlobalDialogLifecycleObserver : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        GlobalDialogUtil.globalDialogState.observe(owner, { it.invoke(owner as Activity) })
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onStop(owner)
        GlobalDialogUtil.globalDialogState.removeObservers(owner)
    }
}

