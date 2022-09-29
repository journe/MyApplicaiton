package com.jour.myapplication.ui

import android.content.Intent
import android.media.RemoteController
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jour.myapplication.R.layout
import com.jour.myapplication.utils.XiaoAiAssist.PACKAGE_NAME
import com.jour.myapplication.utils.XiaoAiAssist.RADIO_ACTION_JUMP_ALBUM
import com.jour.myapplication.utils.XiaoAiAssist.RADIO_ACTION_PLAY
import com.jour.myapplication.utils.XiaoAiAssist.RADIO_ACTION_SEARCH_TAG
import com.jour.myapplication.utils.XiaoAiAssist.SERVICE_NAME
import kotlinx.android.synthetic.main.activity_xiao_ai_test.audio_btn
import kotlinx.android.synthetic.main.activity_xiao_ai_test.play_btn
import kotlinx.android.synthetic.main.activity_xiao_ai_test.search_btn
import org.json.JSONArray
import org.json.JSONObject

class XiaoAiTestActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_xiao_ai_test)
    play_btn.setOnClickListener { getAudioPlayerService("4495") }
    search_btn.setOnClickListener { search("大耳朵图图") }
    audio_btn.setOnClickListener { audioDetail("4495") }
  }

  private fun getAudioPlayerService(id: String) {
    val strRadioList: String
    val jsonArray = JSONArray()
    val jsonObject = JSONObject()
    jsonObject.put("mediaId", id)
    jsonArray.put(jsonObject)
    val json = JSONObject()
    json.put("mediaList", jsonArray)
    json.put("isOnlyPreLoad", true)
    strRadioList = json.toString()
    val intent = Intent()
    intent.setClassName(PACKAGE_NAME, SERVICE_NAME)
    intent.action = PACKAGE_NAME + RADIO_ACTION_PLAY
    intent.data = Uri.parse(strRadioList)
    startService(intent)
  }

  private fun search(key: String) {
    val intent = Intent()
    intent.setPackage(PACKAGE_NAME)
    intent.action = PACKAGE_NAME + RADIO_ACTION_SEARCH_TAG
    intent.putExtra("tag", "audio")
    intent.putExtra("key", key)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
  }

  private fun audioDetail(id: String) {
    val intent = Intent()
    intent.setPackage(PACKAGE_NAME)
    intent.action = PACKAGE_NAME + RADIO_ACTION_JUMP_ALBUM
    intent.putExtra("id", id)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
  }
}
