package com.example.jour.myapplication.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.MessagingStyle
import androidx.core.app.Person
import androidx.appcompat.app.AppCompatActivity
import com.example.jour.myapplication.R
import com.example.jour.myapplication.utils.notification.PlayingNotification
import com.example.jour.myapplication.utils.notification.PlayingNotificationImpl
import com.example.jour.myapplication.utils.notification.PlayingNotificationImpl24
import kotlinx.android.synthetic.main.activity_notification.button24
import kotlinx.android.synthetic.main.activity_notification.button_message
import kotlinx.android.synthetic.main.activity_notification.button_normal
import org.jetbrains.anko.sdk27.coroutines.onClick

class NotificationActivity : AppCompatActivity() {

  private lateinit var playingNotification: PlayingNotification
  private val TIM_NOTIFICATION_ID = 520
  private val TIM_NOTIFICATION_NAME = "IM消息"
  private val TIM_NOTIFICATION_DESC = "潮玩族IM消息通知"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notification)
    button24.setOnClickListener {
      updateNotification24()
    }
    button_normal.setOnClickListener {
      updateNotification()
    }

    button_message.onClick {
      sendMessage()
    }
  }

  private fun sendMessage() {
    val mNotificationManager: NotificationManager =
      getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val builder: NotificationCompat.Builder =
      NotificationCompat.Builder(this, TIM_NOTIFICATION_NAME)

    if (Build.VERSION.SDK_INT >= 26) {
      val channel =
        NotificationChannel(TIM_NOTIFICATION_NAME, "潮玩族IM消息通知", NotificationManager.IMPORTANCE_HIGH)
      mNotificationManager.createNotificationChannel(channel)
    }
    val mUser = Person.Builder()
        .setName("userDisplayName")
        .build()
    val mUserme = Person.Builder()
        .setName("mimename")
        .build()
    builder.setContentTitle("title") //设置通知栏标题
        .setContentText("content")
//        .setContentIntent(pendingIntent) //设置通知栏单击意图
        .setCategory(Notification.CATEGORY_MESSAGE)
//            .setNumber(++pushNum) //设置通知集合的数量
//        .setTicker(senderNickName.toString() + ":" + content) //通知首次出现在通知栏，带上升动画效果的
        .setWhen(System.currentTimeMillis()) //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
        .setDefaults(Notification.DEFAULT_ALL) //向通知添加声音、闪灯和振动效果
        .setSmallIcon(R.drawable.ic_menu_send) //设置通知小 ICON
    builder.setStyle(
        MessagingStyle(mUserme)
            .addMessage(
                "addMessageA", System.currentTimeMillis()
                .toLong(), mUser
            )
            .addMessage(
                "addMessageB", System.currentTimeMillis()
                .toLong(), "sender2"
            )
            .addMessage(
                "addMessageC", System.currentTimeMillis()
                .toLong(), "sender3"
            )
            .addMessage(
                "addMessageD", System.currentTimeMillis()
                .toLong(), "sender4"
            )
            .setConversationTitle("ConversationTitle")
    )

    val notify: Notification = builder.build()
    notify.flags = notify.flags or Notification.FLAG_AUTO_CANCEL
    mNotificationManager.notify(TIM_NOTIFICATION_ID, notify)
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
