package com.example.lvchen.myapplication.echo

import android.os.Process
import com.tencent.bugly.crashreport.CrashReport
//import com.google.firebase.crashlytics.FirebaseCrashlytics
//import com.google.firebase.crashlytics.ktx.crashlytics
//import com.google.firebase.ktx.Firebase
import tech.echoing.kuril.BuildConfig

/**
 * 在Application中统一捕获异常，保存到文件中下次再打开时上传
 */
object KurilCrashHandler : Thread.UncaughtExceptionHandler {
    /**
     * 系统默认的UncaughtException处理类
     */
    private val mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
    private var caughtTask: CaughtTask? = null
    private var logInterface: KurilLogInterface? = null

//    private val crashlytics: FirebaseCrashlytics = Firebase.crashlytics


    /**
     * 初始化,注册Context对象,
     * 获取系统默认的UncaughtException处理器,
     * 设置该CrashHandler为程序的默认处理器
     */
    fun regist(kurilLogInterface: KurilLogInterface?, caughtTask: CaughtTask?) {
        this.logInterface = kurilLogInterface
        this.caughtTask = caughtTask
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    override fun uncaughtException(
        thread: Thread,
        ex: Throwable
    ) {
        if (!handleException(ex)) {
            //系统默认的异常处理器来处理
            if (null != caughtTask) {
                caughtTask!!.caugthAfterDo(ex)
            }
            mDefaultHandler.uncaughtException(thread, ex)
        } else {
            //自己处理了异常，不再向上抛异常
//            try {
//                Thread.sleep(3000)
//            } catch (e: InterruptedException) {
//            }
            if (null != caughtTask) {
                caughtTask!!.caugthAfterDo(ex)
            } else {
                Process.killProcess(Process.myPid())
                System.exit(10)
            }
        }
    }

    /**
     * 自定义错误处理,收集错误信息
     * 发送错误报告等操作均在此完成.
     *
     * @return true代表处理该异常，不再向上抛异常，
     * false代表不处理该异常(可以将该log信息存储起来)然后交给上层(这里就到了系统的异常处理)去处理，
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        //        final String msg = ex.getLocalizedMessage();
        val stack = ex.stackTrace
        val message = ex.message
//        crashlytics.recordException(ex)
        CrashReport.postCatchedException(ex)

//        Logger.d(message)

//        object : Thread() {
//            override fun run() {
//                Looper.prepare()
//                val buffer =
//                    StringBuffer(message.toString()).append("\n")
//                if (null != stack) {
//                    for (stackTraceElement in stack) {
//                        buffer.append(stackTraceElement.toString()).append("\n")
//                    }
//                }
//                //                MyApplication.showToastCenter("程序出错啦:" + message);
//                //                ToastUtils.showText(mContext,"程序出错啦:" + message , ToastUtils.LENGTH_LONG);
//                //                可以只创建一个文件，以后全部往里面append然后发送，这样就会有重复的信息，个人不推荐
//                //                String fileName = "crash-" + System.currentTimeMillis()  + ".log";
//                //                File file = new File(Environment.getExternalStorageDirectory(), fileName);
//                //                try {
//                //                    FileOutputStream fos = new FileOutputStream(file,true);
//                //                    fos.write(message.getBytes());
//                //                    for (int i = 0; i < stack.length; i++) {
//                //                        fos.write(stack[i].toString().getBytes());
//                //                    }
//                //                    fos.flush();
//                //                    fos.close();
//                //                } catch (Exception e) {
//                //                }
//                if (null != logInterface) {
//                    //				    LogAps.i("ApsCrashHandler", "save ex log");
//                    logInterface!!.save(buffer.toString())
//                }
//                //				android.os.Process.killProcess(android.os.Process.myPid());
//                //				System.exit(10);
//                Looper.loop()
//            }
//        }.start()
        return BuildConfig.DEBUG.not()
    }

    fun sendLog() {
        if (null != logInterface) {
            logInterface!!.sendLog()
        }
    }

    fun save(message: String?) {
        if (null != logInterface) {
            logInterface!!.save(message)
        }
    }

    /**
     * 异常后的程序处理
     *
     * @author xiongfei.miao@gmail.com
     */
    interface CaughtTask {
        fun caugthAfterDo(throwable: Throwable?)
    }
}