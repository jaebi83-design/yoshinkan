package com.yoshinkan

import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun VideoPlayerScreen(
    attack: String,
    technique: String,
    energy: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val viewModelInstance: VideoViewModel = viewModel()
    var sliderPosition by remember { mutableLongStateOf(0L) }
    var isSliderDragging by remember { mutableStateOf(false) }

    // Load video when screen appears
    LaunchedEffect(attack, technique, energy) {
        viewModelInstance.loadVideo(context, attack, technique, energy)
    }

    // Update slider position as video plays (but not while user is dragging)
    LaunchedEffect(viewModelInstance.currentPosition) {
        if (!isSliderDragging) {
            sliderPosition = viewModelInstance.currentPosition
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Header with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                "Now Playing: $attack / $technique / $energy",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
        }

        // Video display area with VideoView
        if (viewModelInstance.errorMessage == null) {
            AndroidView(
                factory = { context ->
                    VideoView(context).apply {
                        viewModelInstance.videoView = this
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(Color.Black)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    viewModelInstance.errorMessage ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Progress slider
        if (viewModelInstance.errorMessage == null && viewModelInstance.videoDuration > 0) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Slider(
                    value = sliderPosition.toFloat(),
                    onValueChange = { newValue ->
                        isSliderDragging = true  // Set flag immediately when user starts dragging
                        sliderPosition = newValue.toLong()
                        viewModelInstance.seekTo(newValue.toLong())
                    },
                    onValueChangeFinished = {
                        isSliderDragging = false  // Clear flag when user finishes dragging
                    },
                    valueRange = 0f..viewModelInstance.videoDuration.toFloat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        formatTime(sliderPosition),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                    Text(
                        formatTime(viewModelInstance.videoDuration),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
            }
        }

        // Control buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = { viewModelInstance.play() },
                enabled = viewModelInstance.errorMessage == null && !viewModelInstance.isPlaying
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play",
                    tint = if (viewModelInstance.errorMessage == null) Color.White else Color.Gray
                )
            }

            IconButton(
                onClick = { viewModelInstance.pause() },
                enabled = viewModelInstance.isPlaying
            ) {
                Icon(
                    imageVector = Icons.Filled.Pause,
                    contentDescription = "Pause",
                    tint = if (viewModelInstance.isPlaying) Color.White else Color.Gray
                )
            }

            IconButton(
                onClick = { viewModelInstance.stop() },
                enabled = viewModelInstance.errorMessage == null
            ) {
                Icon(
                    imageVector = Icons.Filled.Stop,
                    contentDescription = "Stop",
                    tint = if (viewModelInstance.errorMessage == null) Color.White else Color.Gray
                )
            }
        }

        // Error message display (if any)
        if (viewModelInstance.errorMessage != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF331111))
                    .padding(16.dp)
            ) {
                Text(
                    viewModelInstance.errorMessage ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red
                )
            }
        }

        // Video selection info
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFF1A1A2E))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    "Selection Details:",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
                Text(
                    "Attack: $attack",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    "Technique: $technique",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    "Energy: $energy",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Back button (additional)
        Button(
            onClick = onBackClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Back to Selection")
        }
    }
}

/**
 * Format milliseconds to MM:SS format
 */
fun formatTime(milliseconds: Long): String {
    val seconds = (milliseconds / 1000) % 60
    val minutes = (milliseconds / 1000) / 60
    return String.format("%02d:%02d", minutes, seconds)
}
