package com.jour.myapplication.ui

import android.os.Bundle
import com.jour.myapplication.base.BaseActivity
import com.jour.myapplication.databinding.ActivityEditTextBinding

class AActivity : BaseActivity() {
    lateinit var binding: ActivityEditTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}