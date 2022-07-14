package com.example.jour.myapplication

import android.app.Application

//import cn.idaddy.android.opensdk.lib.IdaddySdk
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * Created by journey on 2018/8/1.
 */
class MyApplication : Application() {
  override fun onCreate() {
    super.onCreate()
//    IdaddySdk.init(this, false)
    Logger.addLogAdapter(AndroidLogAdapter())
    ARouter.init(this)
  }
}
