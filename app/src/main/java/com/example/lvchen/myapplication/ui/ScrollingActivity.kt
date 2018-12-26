package com.example.lvchen.myapplication.ui

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.lvchen.myapplication.R.layout
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_scrolling)
    setSupportActionBar(story_detail_toolbar)
    story_detail_fab.setOnClickListener { view ->
      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null)
          .show()
    }

    story_detail_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)
    story_detail_toolbar_layout.setExpandedTitleColor(Color.BLUE)
    story_detail_toolbar_layout.title = "testtitleAppCompatActivity"
//    story_detail_toolbar.title = "testtitleAppCompatActivity"
  }
}
