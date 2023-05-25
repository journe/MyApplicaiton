package com.jour.myapplication.ui.topiclist

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import com.jour.myapplication.R

class TopicListActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_topic_list)
    val sectionsPagerAdapter =
      SectionsPagerAdapter(this, supportFragmentManager)
    val viewPager: androidx.viewpager.widget.ViewPager = findViewById(R.id.view_pager)
    viewPager.adapter = sectionsPagerAdapter
    val tabs: TabLayout = findViewById(R.id.tabs)
    tabs.setupWithViewPager(viewPager)
    val fab: FloatingActionButton = findViewById(R.id.fab)

    fab.setOnClickListener { view ->
      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null)
          .show()
    }
  }
}