package com.max.effem

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.max.effem.databinding.ActivityHomeBinding


class HomeActivity : Activity() {
    private val _player by lazy { ExoPlayer.Builder(applicationContext).build() }
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val playbackStateListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                ExoPlayer.STATE_BUFFERING -> {
                    binding.progressBar.isVisible = true
                }
                else -> binding.progressBar.isVisible = false
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            Log.d("TAG", "changed state to ${error.toString()}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        window?.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

            var flags: Int = decorView.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            decorView.systemUiVisibility = flags
        }
        setContentView(binding.root)
        binding.apply {
            titleTv.setOnClickListener { _player.seekForward() }
            titleTv.isSelected = true
            with(playerView) {
                val powerOffBtn = findViewById<ImageView>(R.id.powerOffBtn)
                powerOffBtn.setOnClickListener {
                    finish()
                }
                player = _player
                val mediaItem: MediaItem = MediaItem.fromUri(Uri.parse(STREAM_URL))
                with(_player) {
                    controllerHideOnTouch = false
                    controllerShowTimeoutMs = 0
                    addListener(playbackStateListener)
                    playWhenReady = true
                    setMediaItem(mediaItem)
                    prepare()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _player.release()
    }

    companion object {
        const val STREAM_URL = "https://air.pc.cdn.bitgravity.com/air/live/pbaudio230/chunklist.m3u8"
            //"https://strw1.openstream.co/1451"
    // "http://212.83.138.48:8052/stream?type=http&nocache=42"
            //"http://s7.voscast.com:7812/;stream1662670338728/1"
            //"https://eu10.fastcast4u.com/clubfmuae"
            //"https://bcovlive-a.akamaihd.net/19b535b7499a4719a5c19e043063f5d9/ap-southeast-1/6034685947001/profile_1/chunklist.m3u8"
            //"https://stream-32.zeno.fm/pbqv4u9k0k0uv?zs=ZhHW_AF_Q2aDQ3PCs2TqFw"
        // "https://air.pc.cdn.bitgravity.com/air/live/pbaudio230/chunklist.m3u8"
    }
}