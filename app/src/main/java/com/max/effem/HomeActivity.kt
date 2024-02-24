package com.max.effem

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import com.egeniq.exovisualizer.FFTAudioProcessor
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.*
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector
import com.max.effem.RadioListAdapter.Companion.radioList
import com.max.effem.data.RadioData
import com.max.effem.databinding.ActivityHomeBinding


class HomeActivity : Activity() {
    private val _player by lazy {
        ExoPlayer.Builder(applicationContext).build()
    }
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val radioAdapter by lazy {
        RadioListAdapter {
            playMedia(it)
        }
    }
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

    private fun playMedia(data: RadioData) {
        binding.titleTv.text = data.name.lowercase()
        with(_player) {
            clearMediaItems()
            val mediaItem: MediaItem = MediaItem.fromUri(Uri.parse(data.url))
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
       /* window?.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

            var flags: Int = decorView.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            decorView.systemUiVisibility = flags
        }*/
        setContentView(binding.root)
        binding.apply {
            rv.adapter = radioAdapter
            titleTv.setOnClickListener { _player.seekForward() }
            titleTv.isSelected = true
            //exoVisualizer.processor = fftAudioProcessor
            with(playerView) {
                val powerOffBtn = findViewById<ImageView>(R.id.powerOffBtn)
                powerOffBtn.setOnClickListener {
                    finish()
                }
                controllerHideOnTouch = false
                player = _player
                playMedia(radioList[0])
                with(_player) {
                    controllerShowTimeoutMs = 0
                    addListener(playbackStateListener)
                    playWhenReady = true
                }
            }
            privacyBtn.setOnClickListener {
                // Replace "https://www.example.com" with the URL of the website you want to open
                val websiteUrl = "https://sites.google.com/view/effem/home"

                // Create an Intent with ACTION_VIEW and the website's URI
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))

                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _player.release()
    }
}