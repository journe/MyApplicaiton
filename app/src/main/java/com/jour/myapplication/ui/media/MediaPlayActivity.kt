package com.jour.myapplication.ui.media

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.jour.myapplication.databinding.ActivityMediaPlayBinding


class MediaPlayActivity : AppCompatActivity() {
    lateinit var binding: ActivityMediaPlayBinding

    val coroutinePlayer = CoroutinePlayer()
    val asyncPlayer = AsyncPlayer()

    private val music1 = "https://cck-peiyin.oss-cn-hangzhou.aliyuncs.com/music/220426022636190.mp3"
    private val music2 = "https://cck-peiyin.oss-cn-hangzhou.aliyuncs.com/music/220426023448242.mp3"
    val videoUrl =
        "https://cck-peiyin.oss-cn-hangzhou.aliyuncs.com/vedio/220428032047987.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {

        binding.button.setOnClickListener {
            play(it)
        }
        binding.button2.setOnClickListener {
            play2(it)
        }
        binding.button3.setOnClickListener {
            stop()
        }

        binding.button4.setOnClickListener {
            release()
        }

        binding.button5.setOnClickListener {
            startService(Intent(this, MyService::class.java).apply {
                action = MyService.ACTION_PLAY
                data = Uri.parse(music1)
            }
            )
        }
        binding.button8.setOnClickListener {
            startService(Intent(this, MyService::class.java).apply {
                action = MyService.ACTION_PLAY
                data = Uri.parse(music2)
            }
            )
        }
        binding.button6.setOnClickListener {
            stopService(
                Intent(this, MyService::class.java)
            )
        }

        binding.button7.setOnClickListener {

            val videoView = binding.videoView
            videoView.setVideoPath(videoUrl)
            videoView.start()
            videoView.setOnPreparedListener {
                it.isLooping = true
            }
            val mediaController = MediaController(this)
            videoView.setMediaController(mediaController)
            mediaController.setMediaPlayer(videoView)
        }



    }

    private fun release() {
        coroutinePlayer.release()
    }

    private fun play2(it: View) {
        asyncPlayer.play(it.context, music1, false)
    }

    private fun stop() {
        coroutinePlayer.stop()
        asyncPlayer.stop()
    }

    private fun play(it: View) {
        coroutinePlayer.play(it.context, music1, false)
    }
}