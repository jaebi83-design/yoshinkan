package com.yoshinkan

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.io.File

class VideoViewModel : ViewModel() {
    private var mediaPlayer: MediaPlayer? = null

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
            try {
                val assetFileDescriptor = context.assets.openFd("videos/$videoFileName")
                loadMediaPlayer(assetFileDescriptor)
                return
            } catch (e: Exception) {
                // Asset not found, try internal storage
            }

            // Alternative: Check in app's internal files directory
            val videoFile = File(context.filesDir, videoFileName)
            if (videoFile.exists()) {
                loadMediaPlayer(videoFile.absolutePath)
                return
            }

            // Alternative: Check in app cache directory
            val cacheFile = File(context.cacheDir, videoFileName)
            if (cacheFile.exists()) {
                loadMediaPlayer(cacheFile.absolutePath)
                return
            }

            errorMessage = "Video not found: $videoFileName"

        } catch (e: Exception) {
            errorMessage = "Error loading video: ${e.message}"
        }
    }

    private fun loadMediaPlayer(filePath: String) {
        try {
            release() // Clean up previous player

            mediaPlayer = MediaPlayer().apply {
                setDataSource(filePath)
                prepareAsync()
                setupMediaPlayerListeners()
            }
        } catch (e: Exception) {
            errorMessage = "Failed to load video: ${e.message}"
        }
    }

    private fun loadMediaPlayer(assetFileDescriptor: android.content.res.AssetFileDescriptor) {
        try {
            release() // Clean up previous player

            mediaPlayer = MediaPlayer().apply {
                setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.length)
                prepareAsync()
                setupMediaPlayerListeners()
            }
        } catch (e: Exception) {
            errorMessage = "Failed to load video: ${e.message}"
        }
    }

    private fun MediaPlayer.setupMediaPlayerListeners() {
        setOnPreparedListener { mp ->
            videoDuration = mp.duration.toLong()
        }
        setOnErrorListener { _, what, extra ->
            errorMessage = "MediaPlayer Error: $what (extra: $extra)"
            true
        }
        setOnCompletionListener {
            this@VideoViewModel.isPlaying = false
            this@VideoViewModel.currentPosition = 0L
        }
    }

    fun play() {
        try {
            errorMessage = null
            if (mediaPlayer?.isPlaying == false) {
                mediaPlayer?.start()
                isPlaying = true
                startProgressUpdater()
            } else if (mediaPlayer == null) {
                errorMessage = "Video not loaded"
            }
        } catch (e: Exception) {
            errorMessage = "Playback error: ${e.message}"
        }
    }

    private fun startProgressUpdater() {
        // Update position every 100ms while playing
        Thread {
            while (isPlaying && mediaPlayer != null) {
                try {
                    currentPosition = mediaPlayer?.currentPosition?.toLong() ?: 0L
                    Thread.sleep(100)
                } catch (e: Exception) {
                    break
                }
            }
        }.start()
    }

    fun pause() {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                isPlaying = false
            }
        } catch (e: Exception) {
            errorMessage = "Pause error: ${e.message}"
        }
    }

    fun seekTo(position: Long) {
        try {
            mediaPlayer?.seekTo(position.toInt())
            currentPosition = position
        } catch (e: Exception) {
            errorMessage = "Seek error: ${e.message}"
        }
    }

    fun stop() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.prepareAsync()
            isPlaying = false
            currentPosition = 0L
        } catch (e: Exception) {
            errorMessage = "Stop error: ${e.message}"
        }
    }

    fun release() {
        try {
            mediaPlayer?.apply {
                if (isPlaying) stop()
                release()
            }
            mediaPlayer = null
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
