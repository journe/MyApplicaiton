package com.example.lvchen.myapplication.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.lvchen.myapplication.R
import com.example.lvchen.myapplication.utils.notification.PlayingNotification
import com.example.lvchen.myapplication.utils.notification.PlayingNotificationImpl
import com.example.lvchen.myapplication.utils.notification.PlayingNotificationImpl24
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {

  private lateinit var playingNotification: PlayingNotification

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notification)
    button24.setOnClickListener {
      updateNotification24()
    }
    button_normal.setOnClickListener {
      updateNotification()
    }
  }

  private fun updateNotification24() {
    playingNotification = PlayingNotificationImpl24()
    playingNotification.init(this)
    playingNotification.update()
  }

  private fun updateNotification() {
    playingNotification = PlayingNotificationImpl()
    playingNotification.init(this)
    playingNotification.update()
  }
}
