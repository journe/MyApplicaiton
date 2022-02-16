package com.example.lvchen.myapplication

import android.app.Application

//import cn.idaddy.android.opensdk.lib.IdaddySdk
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by journey on 2018/8/1.
 */
class MyApplication : Application() {
  override fun onCreate() {
    super.onCreate()
//    IdaddySdk.init(this, false)
    ARouter.init(this)
  }
}
