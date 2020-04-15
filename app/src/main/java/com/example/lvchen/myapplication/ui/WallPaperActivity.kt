package com.example.lvchen.myapplication.ui

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lvchen.myapplication.R
import kotlinx.android.synthetic.main.activity_wall_paper.wallpaper_btn
import kotlinx.android.synthetic.main.activity_wall_paper.wallpaper_iv
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class WallPaperActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_wall_paper)
    wallpaper_btn.setOnClickListener {
      val wallpaperManager = WallpaperManager
          .getInstance(applicationContext)
      // 获取当前壁纸
      val drawable = wallpaperManager.drawable as BitmapDrawable
      Log.d("MainActivity", "drawable.getIntrinsicHeight():" + drawable.intrinsicHeight)
      wallpaper_iv.setImageDrawable(drawable)
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
