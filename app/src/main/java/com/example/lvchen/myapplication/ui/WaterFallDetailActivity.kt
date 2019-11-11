package com.example.lvchen.myapplication.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewTreeObserver
import com.bumptech.glide.Glide
import com.example.lvchen.myapplication.R
import kotlinx.android.synthetic.main.activity_water_fall_detail.topic_image_detail_iv



class WaterFallDetailActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_water_fall_detail)
    val url = intent.getStringExtra("url")
    Glide.with(this@WaterFallDetailActivity)
        .load(url)
        .into(topic_image_detail_iv)
    postponeEnterTransition()
    topic_image_detail_iv.viewTreeObserver
        .addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
              override fun onPreDraw(): Boolean {
                topic_image_detail_iv.viewTreeObserver
                    .removeOnPreDrawListener(this)
                startPostponedEnterTransition()
                return true
              }
            }
        )
  }
}
