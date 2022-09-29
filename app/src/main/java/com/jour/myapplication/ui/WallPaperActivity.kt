package com.jour.myapplication.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jour.myapplication.databinding.ActivityWallPaperBinding
import com.permissionx.guolindev.PermissionX
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class WallPaperActivity : AppCompatActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWallPaperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.wallpaperBtn.setOnClickListener {
            val wallpaperManager = WallpaperManager
                .getInstance(applicationContext)


            val requestList = ArrayList<String>()
            requestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            if (requestList.isNotEmpty()) {
                PermissionX.init(this)
                    .permissions(requestList)
                    .explainReasonBeforeRequest()
                    .onExplainRequestReason { scope, deniedList ->
                        val message = "需要您同意以下权限才能正常使用"
                        scope.showRequestReasonDialog(deniedList, message, "同意", "拒绝")
                    }
                    .request { allGranted, grantedList, deniedList ->
                        if (allGranted) {
                            // 获取当前壁纸
                            val drawable = wallpaperManager.drawable as BitmapDrawable

                            Log.d(
                                "MainActivity",
                                "drawable.getIntrinsicHeight():" + drawable.intrinsicHeight
                            )
                            binding.wallpaperIv.setImageDrawable(drawable)
                            val bitmap = drawable.bitmap
                            var fos: FileOutputStream? = null
                            try {
                                fos = openFileOutput("image.png", Context.MODE_PRIVATE)
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                            } catch (e: FileNotFoundException) {
                            } finally {
                                if (fos != null) {
                                    try {
                                        fos.flush()
                                        fos.close()
                                    } catch (e: IOException) {
                                    }

                                }
                            }
                        }
                    }
            }

        }
    }
}
