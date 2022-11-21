package com.jour.myapplication.ui.picture

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import coil.load
import com.luck.picture.lib.engine.CropFileEngine
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropImageEngine
import java.io.File

/**
 * 自定义裁剪
 */
class ImageFileCropEngine : CropFileEngine {
    override fun onStartCrop(
        fragment: Fragment,
        srcUri: Uri,
        destinationUri: Uri,
        dataSource: ArrayList<String>,
        requestCode: Int
    ) {
        val options: UCrop.Options = buildOptions(fragment.requireContext())
        val uCrop = UCrop.of(srcUri, destinationUri, dataSource)
        uCrop.withOptions(options)
        uCrop.setImageEngine(object : UCropImageEngine {
            override fun loadImage(context: Context, url: String, imageView: ImageView) {
                if (!ImageLoaderUtils.assertValidRequest(context)) {
                    return
                }
                imageView.load(url)
            }

            override fun loadImage(
                context: Context,
                url: Uri,
                maxWidth: Int,
                maxHeight: Int,
                call: UCropImageEngine.OnCallbackListener<Bitmap>
            ) {
            }
        })
        uCrop.start(fragment.requireActivity(), fragment, requestCode)
    }

    /**
     * 配制UCrop，可根据需求自我扩展
     *
     * @return
     */
    private fun buildOptions(context: Context): UCrop.Options {
        val options = UCrop.Options()
        options.setHideBottomControls(true)
        options.setFreeStyleCropEnabled(false)
        options.setShowCropFrame(false)
        options.setShowCropGrid(false)
        options.setCircleDimmedLayer(true)
        options.withAspectRatio(1f, 1f)
        options.setCropOutputPathDir(getSandboxPath(context))
        options.isCropDragSmoothToCenter(false)
        options.setMaxScaleMultiplier(100f)
        return options
    }

    /**
     * 创建自定义输出目录
     *
     * @return
     */
    private fun getSandboxPath(context: Context): String {
        val externalFilesDir: File = context.externalCacheDir ?: context.cacheDir
        val customFile = File(externalFilesDir.absolutePath, "crop")
        if (!customFile.exists()) {
            customFile.mkdirs()
        }
        return customFile.absolutePath + File.separator
    }
}