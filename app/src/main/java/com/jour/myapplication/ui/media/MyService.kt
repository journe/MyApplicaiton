package com.jour.myapplication.ui.media

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.IBinder
import android.media.AsyncPlayer
import android.net.Uri
import android.os.PowerManager
import com.jour.myapplication.R
import com.orhanobut.logger.Logger


class MyService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    companion object {
        const val ACTION_PLAY: String = "com.example.action.PLAY"
    }

    private var mMediaPlayer: MediaPlayer? = null

    private var asyncPlayer: AsyncPlayer = AsyncPlayer("tag")

    fun initMediaPlayer() {
        mMediaPlayer?.setOnErrorListener(this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val action: String = intent.action.toString()
        val url = intent.getStringExtra("url")

        Logger.d(intent.data.toString())
        when (action) {
            ACTION_PLAY -> {
//                asyncPlayer.play(this, url, false)
//                asyncPlayer.play(this, Uri.parse(url), false, AudioManager.STREAM_MUSIC)
                asyncPlayer.play(this, intent.data, false, AudioManager.STREAM_MUSIC)

//                mMediaPlayer = MediaPlayer().apply {
//                    setAudioStreamType(AudioManager.STREAM_MUSIC)
//                    setDataSource(url)
//                    setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
//                    setOnErrorListener(this@MyService)
//                }
//                mMediaPlayer?.apply {
//                    setOnPreparedListener(this@MyService)
//                    prepare() // prepare async to not block main thread
//                    start()
//                }
            }
        }
        return START_STICKY_COMPATIBILITY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    /** Called when MediaPlayer is ready */
    override fun onPrepared(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
    }

    override fun onError(mp: MediaPlayer?, p1: Int, p2: Int): Boolean {
        // ... react appropriately ...
        // The MediaPlayer has moved to the Error state, must be reset!
        mp?.reset()
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("onDestroy")
        asyncPlayer.stop()
        mMediaPlayer?.release()
    }
}
