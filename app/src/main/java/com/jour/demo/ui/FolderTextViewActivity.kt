package com.jour.demo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jour.demo.databinding.ActivityFolderTextViewBinding

class FolderTextViewActivity : AppCompatActivity() {
    private lateinit var bingding: ActivityFolderTextViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bingding = ActivityFolderTextViewBinding.inflate(layoutInflater)
        setContentView(bingding.root)
//        bingding.expandText.setText(resources.getText(R.string.medium_text).toString())
    }
}