package com.jour.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jour.myapplication.R
import com.jour.myapplication.databinding.ActivityFolderTextViewBinding
import kotlinx.android.synthetic.main.notification.view.*

class FolderTextViewActivity : AppCompatActivity() {
    private lateinit var bingding: ActivityFolderTextViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bingding = ActivityFolderTextViewBinding.inflate(layoutInflater)
        setContentView(bingding.root)
//        bingding.expandText.setText(resources.getText(R.string.medium_text).toString())
    }
}