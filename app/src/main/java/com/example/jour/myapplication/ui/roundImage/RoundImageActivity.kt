package com.example.jour.myapplication.ui.roundImage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jour.myapplication.databinding.ActivityRoundImageBinding

class RoundImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoundImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoundImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}