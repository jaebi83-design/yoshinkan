package com.yoshinkan

import android.content.Context
import android.media.MediaPlayer
import android.widget.VideoView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.io.File

class VideoViewModel : ViewModel() {
    private var mediaPlayer: MediaPlayer? = null
    var videoView: VideoView? = null
    private var lastSeekTime = 0L

    var isPlaying by mutableStateOf(false)
    var currentPosition by mutableStateOf(0L)
    var videoDuration by mutableStateOf(0L)
    var errorMessage by mutableStateOf<String?>(null)
    var videoFileName by mutableStateOf("")

    fun loadVideo(
        context: Context,
        attack: String,
        technique: String,
        energy: String
    ) {
        try {
            errorMessage = null

            // Convert display names to UPPERCASE_WITH_UNDERSCORES format
            val attackUpper = attack.replace(" ", "_").uppercase()
            val techniqueUpper = technique.replace(" ", "_").uppercase()
            val energyUpper = energy.uppercase()

            // Build filename: ATTACK_TECHNIQUE_ENERGY.mp4
            videoFileName = "${attackUpper}_${techniqueUpper}_${energyUpper}.mp4"

            // Try to load from assets first
            val videoPath = try {
                // Copy asset to cache for VideoView access
                val assetFileDescriptor = context.assets.openFd("videos/$videoFileName")
                val cacheFile = File(context.cacheDir, videoFileName)

                if (!cacheFile.exists()) {
                    assetFileDescriptor.createInputStream().use { input ->
                        cacheFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                }
                cacheFile.absolutePath
            } catch (e: Exception) {
                // Asset not found, try internal storage
                val videoFile = File(context.filesDir, videoFileName)
                if (videoFile.exists()) {
                    videoFile.absolutePath
                } else {
                    null
                }
            }

            if (videoPath != null) {
                loadVideoView(videoPath)
            } else {
                errorMessage = "Video not found: $videoFileName"
            }

        } catch (e: Exception) {
            errorMessage = "Error loading video: ${e.message}"
        }
    }

    fun loadVideoView(videoPath: String) {
        try {
            videoView?.apply {
                setVideoPath(videoPath)
                setOnPreparedListener { mp ->
                    this@VideoViewModel.videoDuration = mp.duration.toLong()
                    this@VideoViewModel.isPlaying = true
                    start()
                }
                setOnCompletionListener {
                    this@VideoViewModel.isPlaying = false
                    this@VideoViewModel.currentPosition = 0L
                }
                setOnErrorListener { _, what, extra ->
                    this@VideoViewModel.errorMessage = "VideoView Error: $what"
                    true
                }
            }
        } catch (e: Exception) {
            errorMessage = "Failed to load video: ${e.message}"
        }
    }

    fun play() {
        try {
            errorMessage = null
            videoView?.apply {
                if (!isPlaying) {
                    start()
                    this@VideoViewModel.isPlaying = true
                    startProgressUpdater()
                }
            } ?: run {
                errorMessage = "Video not loaded"
            }
        } catch (e: Exception) {
            errorMessage = "Playback error: ${e.message}"
        }
    }

    fun pause() {
        try {
            videoView?.apply {
                if (isPlaying) {
                    pause()
                    this@VideoViewModel.isPlaying = false
                }
            }
        } catch (e: Exception) {
            errorMessage = "Pause error: ${e.message}"
        }
    }

    fun seekTo(position: Long) {
        try {
            val positionMs = position.toInt()
            videoView?.apply {
                seekTo(positionMs)
                this@VideoViewModel.currentPosition = position
                // Record seek time to prevent progress updater from overriding
                this@VideoViewModel.lastSeekTime = System.currentTimeMillis()
            }
        } catch (e: Exception) {
            errorMessage = "Seek error: ${e.message}"
        }
    }

    fun stop() {
        try {
            videoView?.apply {
                pause()  // Pause instead of stopPlayback to keep controls active
                seekTo(0)  // Seek to beginning
                this@VideoViewModel.isPlaying = false
                this@VideoViewModel.currentPosition = 0L
            }
        } catch (e: Exception) {
            errorMessage = "Stop error: ${e.message}"
        }
    }

    private fun startProgressUpdater() {
        // Update position every 100ms while playing
        Thread {
            while (isPlaying && videoView != null) {
                try {
                    val currentTime = System.currentTimeMillis()
                    // Don't update position for 500ms after a seek to give it time to complete
                    if (currentTime - lastSeekTime > 500) {
                        val videoCurrentPos = videoView?.currentPosition?.toLong() ?: 0L
                        // Only update if position is reasonable
                        if (videoCurrentPos >= 0 && Math.abs(videoCurrentPos - currentPosition) > 100) {
                            currentPosition = videoCurrentPos
                        }
                    }
                    Thread.sleep(100)
                } catch (e: Exception) {
                    break
                }
            }
        }.start()
    }

    fun release() {
        try {
            videoView?.apply {
                if (isPlaying) {
                    stopPlayback()
                }
                suspend()
            }
            videoView = null
            isPlaying = false
            currentPosition = 0L
            videoDuration = 0L
        } catch (e: Exception) {
            // Silently handle cleanup errors
        }
    }

    override fun onCleared() {
        release()
        super.onCleared()
    }
}
