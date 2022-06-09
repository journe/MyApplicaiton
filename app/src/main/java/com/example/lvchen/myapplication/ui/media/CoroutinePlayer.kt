package com.example.lvchen.myapplication.ui.media

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.os.SystemClock
import android.util.Log
import kotlinx.coroutines.*
import java.util.*

class CoroutinePlayer(private val mTag: String = "AsyncPlayer") {
    private var job: Job? = null
    private var mPlayer: MediaPlayer? = null

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    data class Command(
        val context: Context? = null,
        val looping: Boolean = false,
        var uri: String = "",
    )


    private fun startSound(cmd: Command) {
        job = coroutineScope.launch {
            try {
                if (mPlayer != null) {
                    mPlayer!!.apply {
                        stop()
                        reset()
                        setDataSource(cmd.uri)
                        prepare()
                        start()
                    }
                } else {
                    mPlayer = MediaPlayer().apply {
                        setAudioStreamType(AudioManager.STREAM_MUSIC)
                        setDataSource(cmd.uri)
                        isLooping = cmd.looping
                        setWakeMode(cmd.context, PowerManager.PARTIAL_WAKE_LOCK)
                        prepare()
                        start()
                    }
                }
            } catch (e: Exception) {
                val str2 = mTag
                Log.w(str2, "error loading sound for " + cmd.uri, e)
            }
        }

    }

    fun play(context: Context, url: String, looping: Boolean) {
        val cmd = Command(uri = url, context = context, looping = looping)
        startSound(cmd)
    }

    fun stop() {
        if (mPlayer == null) {
            Log.w(mTag, "STOP command without a player")
        } else {
            mPlayer!!.stop()
        }
    }

    fun release() {
        job?.cancel()
        if (mPlayer == null) {
            Log.w(mTag, "STOP command without a player")
        } else {
            mPlayer!!.stop()
            mPlayer!!.release()
            mPlayer = null
        }
    }

}