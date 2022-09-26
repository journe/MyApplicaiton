package com.example.jour.myapplication

import android.app.Application
import android.os.Build
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.VideoFrameDecoder

//import cn.idaddy.android.opensdk.lib.IdaddySdk
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * Created by journey on 2018/8/1.
 */
class MyApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
//    IdaddySdk.init(this, false)
        Logger.addLogAdapter(AndroidLogAdapter())
        ARouter.init(this)
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
