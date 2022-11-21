package com.jour.myapplication

//import cn.idaddy.android.opensdk.lib.IdaddySdk
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.VideoFrameDecoder
import com.alibaba.android.arouter.launcher.ARouter
import com.jour.myapplication.base.BaseApplication
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by journey on 2018/8/1.
 */
@HiltAndroidApp
class MyApplication : BaseApplication(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
//    IdaddySdk.init(this, false)
        Logger.addLogAdapter(AndroidLogAdapter())
        ARouter.init(this)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .components {
                add(VideoFrameDecoder.Factory())
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }
}
