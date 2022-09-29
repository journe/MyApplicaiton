package com.jour.myapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewTreeObserver
import com.bumptech.glide.Glide
import com.jour.myapplication.databinding.ActivityWaterFallDetailBinding

class WaterFallDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityWaterFallDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = intent.getStringExtra("url")
        Glide.with(this@WaterFallDetailActivity)
            .load(url)
            .into(binding.topicImageDetailIv)
        postponeEnterTransition()
        binding.topicImageDetailIv.viewTreeObserver
            .addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        binding.topicImageDetailIv.viewTreeObserver
                            .removeOnPreDrawListener(this)
                        startPostponedEnterTransition()
                        return true
                    }
                }
            )
    }
}
