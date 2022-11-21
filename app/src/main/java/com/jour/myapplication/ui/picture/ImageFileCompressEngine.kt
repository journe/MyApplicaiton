package com.jour.myapplication.ui.picture

import android.content.Context
import android.net.Uri
import com.luck.picture.lib.engine.CompressFileEngine
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import top.zibin.luban.Luban
import top.zibin.luban.OnNewCompressListener
import java.io.File


/**
 * 自定义压缩
 */
class ImageFileCompressEngine : CompressFileEngine {
    override fun onStartCompress(
        context: Context,
        source: ArrayList<Uri>,
        call: OnKeyValueResultCallbackListener
    ) {
        Luban.with(context).load(source).ignoreBy(90).setRenameListener { filePath ->
//            val indexOf = filePath.lastIndexOf(".")
//            val postfix = if (indexOf != -1) filePath.substring(indexOf) else ".jpg"
//            DateUtils.getCreateFileName("CMP_") + postfix
            "CMP_" + filePath.substring(filePath.lastIndexOf("/") + 1)
        }.setCompressListener(object : OnNewCompressListener {
            override fun onStart() {}
            override fun onSuccess(source: String, compressFile: File) {
                call.onCallback(source, compressFile.absolutePath)
            }

            override fun onError(source: String, e: Throwable) {
                call.onCallback(source, null)
            }
        }).launch()
    }
}