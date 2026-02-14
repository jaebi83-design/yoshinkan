package com.yoshinkan.ui.screen

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import android.widget.VideoView

@Composable
fun VideoPlaybackScreen(videoFileName: String?, onBack: () -> Unit) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0) }
    var duration by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Video View
        AndroidView(
            factory = { ctx ->
                VideoView(ctx).apply {
                    if (videoFileName != null) {
                        setVideoURI(Uri.parse("file:///path/to/videos/$videoFileName"))
                        requestFocus()
                        setOnPreparedListener { mp ->
                            duration = mp.duration
                            start()
                            isPlaying = true
                        }
                        setOnCompletionListener {
                            isPlaying = false
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Control Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Rewind */ }) {
                Icon(Icons.Default.SkipPrevious, contentDescription = "Rewind")
            }

            IconButton(onClick = { /* Play/Pause */ }) {
                Icon(
                    if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play"
                )
            }

            IconButton(onClick = { /* Forward */ }) {
                Icon(Icons.Default.SkipNext, contentDescription = "Forward")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back Button
        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Back to Selection")
        }
    }
}
