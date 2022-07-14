package com.example.jour.myapplication.utils

/**
 * Created by journey on 2018/12/24.
 */
object XiaoAiAssist {
  const val XIAO_AI_PACKAGE = "com.miui.voiceassist"
  const val PLAYER_STATUS = "com.appshare.android.ilisten.PLAYER_STATUS"
  const val PLAYER_STATUS_PAUSE = 2
  const val PLAYER_STATUS_PLAY = 3
  const val PLAYER_ERROR = "com.appshare.android.ilisten.PLAYER_ERROR"
  //流量不可播放
  val PLAYER_ERROR_NET = -1
  //资源下线
  val PLAYER_ERROR_RESOURCE = -2
  //没有 VIP权限
  const val PLAYER_ERROR_VIP = -3

  const val PACKAGE_NAME = "com.appshare.android.ilisten"
  //    val SERVICE_NAME = "com.appshare.android.app.story.player.AudioPlayerService"
  const val SERVICE_NAME = "com.appshare.android.app.story.player.XiaoAiService"
  const val RADIO_ACTION_PLAY = ".play_audio"
  const val RADIO_ACTION_SEARCH_TAG = ".search_tag"
  const val RADIO_ACTION_JUMP_ALBUM = ".jump_album"

}
