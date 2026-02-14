package com.yoshinkan.model

import kotlinx.serialization.Serializable

@Serializable
data class VideoManifest(
    val videos: List<VideoEntry>
)

@Serializable
data class VideoEntry(
    val fileName: String,
    val attackId: Int,
    val techniqueId: Int,
    val energyId: Int,
    val displayName: String
)
