package com.example.lvchen.myapplication.ui.media

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.os.SystemClock
import android.util.Log
import java.util.*

/* loaded from: classes2.dex */
class AsyncPlayer(private val mTag: String = "AsyncPlayer") {
    private var mPlayer: MediaPlayer? = null
    private var mThread: AsyncPlayerThread? = null
    private var mWakeLock: WakeLock? = null
    private val mCmdQueue = LinkedList<Command>()
    private var mState = STOP

    /* JADX INFO: Access modifiers changed from: private */ /* loaded from: classes2.dex */
    data class Command(
        var code: Int = 0,
        val context: Context? = null,
        val looping: Boolean = false,
        var requestTime: Long = 0,
        var uri: String = "",
    )


    /* JADX INFO: Access modifiers changed from: private */
    fun startSound(cmd: Command) {
        try {
            val player = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(cmd.uri)
                isLooping = cmd.looping
                setWakeMode(cmd.context, PowerManager.PARTIAL_WAKE_LOCK)
                prepare()
                start()
            }

            mPlayer?.release()
            mPlayer = player
            val delay = SystemClock.uptimeMillis() - cmd.requestTime
            if (delay > 1000) {
                val str = mTag
                Log.w(str, "Notification sound delayed by " + delay + "msecs")
            }
        } catch (e: Exception) {
            val str2 = mTag
            Log.w(str2, "error loading sound for " + cmd.uri, e)
        }
    }

    /* JADX INFO: Access modifiers changed from: private */ /* loaded from: classes2.dex */
    inner class AsyncPlayerThread internal constructor() : Thread("AsyncPlayer-$mTag") {
        // java.lang.Thread, java.lang.Runnable
        override fun run() {
            var cmd: Command
            while (true) {
                synchronized(mCmdQueue) { cmd = mCmdQueue.removeFirst() as Command }
                when (cmd.code) {
                    PLAY -> startSound(cmd)
                    STOP -> {
                        if (mPlayer == null) {
                            Log.w(mTag, "STOP command without a player")
                            break
                        } else {
                            val delay = SystemClock.uptimeMillis() - cmd.requestTime
                            if (delay > 1000) {
                                val str = mTag
                                Log.w(str, "Notification stop delayed by " + delay + "msecs")
                            }
                            mPlayer!!.stop()
                            mPlayer!!.release()
                            mPlayer = null
                            break
                        }
                    }

                }
                synchronized(mCmdQueue) {
                    if (mCmdQueue.size == 0) {
                        mThread = null
                        releaseWakeLock()
                        return
                    }
                }
            }
        }
    }

    @Throws(IllegalArgumentException::class)
    fun play(context: Context, url: String, looping: Boolean) {
        val cmd = Command(
            uri = url,
            code = PLAY,
            context = context,
            looping = looping,
            requestTime = SystemClock.uptimeMillis()
        )

        synchronized(mCmdQueue) {
            enqueueLocked(cmd)
            mState = PLAY
        }
    }

    fun stop() {
        synchronized(mCmdQueue) {
            if (mState != STOP) {
                val cmd = Command(
                    requestTime = SystemClock.uptimeMillis(),
                    code = STOP
                )

                enqueueLocked(cmd)
                mState = STOP
            }
        }
    }

    private fun enqueueLocked(cmd: Command) {
        mCmdQueue.add(cmd)
        if (mThread == null) {
            acquireWakeLock()
            val thread = AsyncPlayerThread()
            mThread = thread
            thread.start()
        }
    }

    fun setUsesWakeLock(context: Context) {
        if (mWakeLock == null && mThread == null) {
            val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, mTag)
            return
        }
        throw RuntimeException("assertion failed mWakeLock=$mWakeLock mThread=$mThread")
    }

    private fun acquireWakeLock() {
        mWakeLock?.acquire()
    }

    /* JADX INFO: Access modifiers changed from: private */
    private fun releaseWakeLock() {
        mWakeLock?.release()
    }

    companion object {
        private const val PLAY = 1
        private const val STOP = 2
        private const val mDebug = false
    }
}