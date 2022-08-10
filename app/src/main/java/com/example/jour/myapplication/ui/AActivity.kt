package com.example.jour.myapplication.ui

import android.os.Bundle
import com.example.jour.myapplication.base.BaseActivity
import com.example.jour.myapplication.databinding.ActivityEditTextBinding

class AActivity : BaseActivity() {
    lateinit var binding: ActivityEditTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}