package com.example.lvchen.myapplication.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.lvchen.myapplication.R.layout

class PActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_p)
//        AssetManager.ensureStringBlocks()
//        val mEnsureStringBlocks = AssetManager::class.java.getDeclaredMethod("ensureStringBlocks", *arrayOfNulls(0))
//        mEnsureStringBlocks.isAccessible = true
    }
}
