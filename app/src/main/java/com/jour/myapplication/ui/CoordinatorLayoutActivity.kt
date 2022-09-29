package com.jour.myapplication.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jour.myapplication.databinding.ActivityScrollingBinding

class CoordinatorLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.storyDetailToolbar)
        binding.storyDetailFab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

        binding.storyDetailToolbarLayout.apply {
            setCollapsedTitleTextColor(Color.BLACK)
            setExpandedTitleColor(Color.BLUE)
            title = "testtitleAppCompatActivity"
        }
    }
}
