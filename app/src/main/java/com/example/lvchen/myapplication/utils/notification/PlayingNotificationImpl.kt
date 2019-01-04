package com.example.lvchen.myapplication.utils.notification

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.text.TextUtils
import android.view.View
import android.widget.RemoteViews
import com.example.lvchen.myapplication.MainActivity
import com.example.lvchen.myapplication.R

class PlayingNotificationImpl : PlayingNotification() {

  @Synchronized override fun update() {
    stopped = false

    val isPlaying = true

    val notificationLayout =
      if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
        RemoteViews(service.packageName, R.layout.notification)
      } else {
        RemoteViews(service.packageName, R.layout.notification_24)
      }
    val notificationLayoutBig = RemoteViews(service.packageName, R.layout.notification_big)

    val title = "爷爷泡的茶"
    val artistName = "周杰伦"
    val albumName = "范特西"

    if (TextUtils.isEmpty(title) && TextUtils.isEmpty(artistName)) {
      notificationLayout.setViewVisibility(R.id.media_titles, View.INVISIBLE)
    } else {
      notificationLayout.setViewVisibility(R.id.media_titles, View.VISIBLE)
      notificationLayout.setTextViewText(R.id.text, artistName)
      notificationLayout.setTextViewText(R.id.text2, albumName)
    }

    if (TextUtils.isEmpty(title) && TextUtils.isEmpty(artistName) && TextUtils.isEmpty(albumName)) {
      notificationLayoutBig.setViewVisibility(R.id.media_titles, View.INVISIBLE)
    } else {
      notificationLayoutBig.setViewVisibility(R.id.media_titles, View.VISIBLE)
      notificationLayoutBig.setTextViewText(R.id.text, artistName)
      notificationLayoutBig.setTextViewText(R.id.text2, albumName)
    }

//    linkButtons(notificationLayout, notificationLayoutBig)

    val action = Intent(service, MainActivity::class.java)
    action.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    val clickIntent = PendingIntent.getActivity(service, 0, action, 0)
    val deleteIntent = buildPendingIntent(service, "MusicService.ACTION_QUIT", null)

    val notification =
      NotificationCompat.Builder(service, PlayingNotification.NOTIFICATION_CHANNEL_ID)
          .setSmallIcon(R.drawable.ic_notification)
          .setContentIntent(clickIntent)
          .setDeleteIntent(deleteIntent)
          .setCategory(NotificationCompat.CATEGORY_SERVICE)
          .setPriority(NotificationCompat.PRIORITY_MAX)
          .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
          .setContent(notificationLayout)
          .setCustomBigContentView(notificationLayoutBig)
          .setOngoing(isPlaying)
          .build()

    val bigNotificationImageSize =
      service.resources.getDimensionPixelSize(R.dimen.notification_big_image_size)

    service.runOnUiThread {
      //      if (bitmap != null) {
//        notificationLayout.setImageViewBitmap(R.id.image, bitmap)
//        notificationLayoutBig.setImageViewBitmap(R.id.image, bitmap)
//      } else {
//        notificationLayout.setImageViewResource(R.id.image, R.drawable.default_album_art)
//        notificationLayoutBig.setImageViewResource(
//            R.id.image,
//            R.drawable.default_album_art
//        )
//      }

      notificationLayout.setImageViewResource(R.id.image, R.drawable.default_album_art)
//      notificationLayoutBig.setImageViewResource(R.id.image, R.drawable.default_album_art)

      if (stopped) {
        return@runOnUiThread  // notification has been stopped before loading was finished
      }
      updateNotifyModeAndPostNotification(notification)
    }
  }

  private fun linkButtons(
    notificationLayout: RemoteViews,
    notificationLayoutBig: RemoteViews
  ) {
    //PendingIntent pendingIntent;
    //
    //final ComponentName serviceName = new ComponentName(service, MusicService.class);
    //
    //// Previous track
    //pendingIntent = buildPendingIntent(service, MusicService.ACTION_REWIND, serviceName);
    //notificationLayout.setOnClickPendingIntent(R.id.action_prev, pendingIntent);
    //notificationLayoutBig.setOnClickPendingIntent(R.id.action_prev, pendingIntent);
    //
    //// Play and pause
    //pendingIntent = buildPendingIntent(service, MusicService.ACTION_TOGGLE_PAUSE, serviceName);
    //notificationLayout.setOnClickPendingIntent(R.id.action_play_pause, pendingIntent);
    //notificationLayoutBig.setOnClickPendingIntent(R.id.action_play_pause, pendingIntent);
    //
    //// Next track
    //pendingIntent = buildPendingIntent(service, MusicService.ACTION_SKIP, serviceName);
    //notificationLayout.setOnClickPendingIntent(R.id.action_next, pendingIntent);
    //notificationLayoutBig.setOnClickPendingIntent(R.id.action_next, pendingIntent);
  }

  private fun buildPendingIntent(
    context: Context,
    action: String,
    serviceName: ComponentName?
  ): PendingIntent {
    val intent = Intent(action)
    intent.component = serviceName
    return PendingIntent.getService(context, 0, intent, 0)
  }

  private fun createBitmap(
    drawable: Drawable,
    sizeMultiplier: Float
  ): Bitmap {
    val bitmap = Bitmap.createBitmap(
        (drawable.intrinsicWidth * sizeMultiplier).toInt(),
        (drawable.intrinsicHeight * sizeMultiplier).toInt(), Bitmap.Config.ARGB_8888
    )
    val c = Canvas(bitmap)
    drawable.setBounds(0, 0, c.width, c.height)
    drawable.draw(c)
    return bitmap
  }
}
