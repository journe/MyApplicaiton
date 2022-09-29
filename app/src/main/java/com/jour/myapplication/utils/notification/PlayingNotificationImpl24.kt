package com.jour.myapplication.utils.notification

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import android.support.v4.media.session.MediaSessionCompat
import android.text.TextUtils
import com.jour.myapplication.R
import com.jour.myapplication.ui.NotificationActivity

class PlayingNotificationImpl24 : PlayingNotification() {

  @Synchronized override fun update() {
    stopped = false
    val title = "爷爷泡的茶"
    val artistName = "周杰伦"
    val albumName = "范特西"
    val isPlaying = true
    val text = if (TextUtils.isEmpty(albumName))
      artistName
    else
      "$artistName - $albumName"

    val playButtonResId = if (isPlaying)
      R.drawable.ic_pause_white_24dp
    else
      R.drawable.ic_play_arrow_white_24dp

    val action = Intent(service, NotificationActivity::class.java)
    action.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    val clickIntent = PendingIntent.getActivity(service, 0, action, 0)

    //final ComponentName serviceName = new ComponentName(service, MusicService.class);
    val intent = Intent()
    //intent.setComponent(serviceName);
    val deleteIntent = PendingIntent.getService(service, 0, intent, 0)

    val bigNotificationImageSize =
      service.resources.getDimensionPixelSize(R.dimen.notification_big_image_size)
    service.runOnUiThread {
      val bitMap = BitmapFactory.decodeResource(
          service.resources,
          R.drawable.default_album_art
      )
      val width = bitMap.width
      val height = bitMap.height
      // 设置想要的大小
      val newWidth = bigNotificationImageSize
      val newHeight = bigNotificationImageSize
      // 计算缩放比例
      val scaleWidth = newWidth.toFloat() / width
      val scaleHeight = newHeight.toFloat() / height
      // 取得想要缩放的matrix参数
      val matrix = Matrix()
      matrix.postScale(scaleWidth, scaleHeight)
      // 得到新的图片
      val bitmap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true)
      val playPauseAction = NotificationCompat.Action(
          playButtonResId,
          service.getString(R.string.action_play_pause),
          retrievePlaybackAction("ACTION_TOGGLE_PAUSE")
      )
      val previousAction = NotificationCompat.Action(
          R.drawable.ic_skip_previous_white_24dp,
          service.getString(R.string.action_previous),
          retrievePlaybackAction("ACTION_REWIND")
      )
      val nextAction = NotificationCompat.Action(
          R.drawable.ic_skip_next_white_24dp,
          service.getString(R.string.action_next),
          retrievePlaybackAction("ACTION_SKIP")
      )
      val builder = NotificationCompat.Builder(service, PlayingNotification.NOTIFICATION_CHANNEL_ID)
          .setSmallIcon(R.drawable.ic_notification)
          .setLargeIcon(bitmap)
          .setContentIntent(clickIntent)
          .setDeleteIntent(deleteIntent)
          .setContentTitle(title)
          .setContentText(text)
          .setOngoing(isPlaying)
          .setShowWhen(false)
          .addAction(previousAction)
          .addAction(playPauseAction)
          .addAction(nextAction)
          .setStyle(
              MediaStyle().setMediaSession(
                  MediaSessionCompat(service, "PlayingNotificationImpl24").sessionToken
              )
                  .setShowActionsInCompactView(0, 1, 2)
          )
          .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

      //Android 9.0 开始自带变色
      if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
        builder.color = Color.WHITE
      }
      updateNotifyModeAndPostNotification(builder.build())
    }
  }

  private fun retrievePlaybackAction(action: String): PendingIntent {
    //final ComponentName serviceName = new ComponentName(service, MusicService.class);
    val intent = Intent(action)
    //intent.setComponent(serviceName);
    return PendingIntent.getService(service, 0, intent, 0)
  }
}
