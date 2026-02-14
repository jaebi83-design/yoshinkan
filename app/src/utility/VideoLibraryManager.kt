package com.yoshinkan.utility

import android.content.Context
import com.yoshinkan.model.VideoManifest
import com.yoshinkan.repository.YoshinkanRepository

object VideoLibraryManager {

    /**
     * Initializes the video library by loading manifest and syncing with database
     * Should be called once on app startup
     */
    suspend fun initializeVideoLibrary(
        context: Context,
        repository: YoshinkanRepository,
        videoDirectoryPath: String
    ): Boolean {
        return try {
            // Load existing manifest
            var manifest = ManifestManager.loadManifest(context)

            // If no manifest exists, generate from directory
            if (manifest == null) {
                manifest = ManifestManager.generateManifestFromDirectory(videoDirectoryPath)
                ManifestManager.saveManifest(context, manifest)
            }

            // Sync manifest with database (optional - for tracking available videos)
            syncManifestWithDatabase(manifest, repository)

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Updates the video library manifest after adding/removing videos
     * Call this when you've manually updated videos in the directory
     */
    suspend fun updateVideoLibrary(
        context: Context,
        repository: YoshinkanRepository,
        videoDirectoryPath: String
    ): Boolean {
        return try {
            // Scan directory for new/updated videos
            val newManifest = ManifestManager.generateManifestFromDirectory(videoDirectoryPath)

            // Save updated manifest
            ManifestManager.saveManifest(context, newManifest)

            // Sync with database
            syncManifestWithDatabase(newManifest, repository)

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Gets the list of available videos from the manifest
     */
    suspend fun getAvailableVideos(context: Context): List<String> {
        val manifest = ManifestManager.loadManifest(context) ?: return emptyList()
        return manifest.videos.map { it.fileName }
    }

    /**
     * Checks if a specific video exists in the manifest
     */
    suspend fun videoExists(context: Context, fileName: String): Boolean {
        val manifest = ManifestManager.loadManifest(context) ?: return false
        return manifest.videos.any { it.fileName == fileName }
    }

    /**
     * Syncs the manifest with the database (optional feature)
     * Useful for tracking available videos without filesystem access
     */
    private suspend fun syncManifestWithDatabase(
        manifest: VideoManifest,
        repository: YoshinkanRepository
    ) {
        // This is optional - you could store available videos in database
        // For now, manifest is the source of truth
        // In the future, you could add a AvailableVideos table
    }

    /**
     * Gets a video entry from the manifest
     */
    suspend fun getVideoEntry(context: Context, attackId: Int, techniqueId: Int, energyId: Int) =
        ManifestManager.loadManifest(context)?.videos?.firstOrNull {
            it.attackId == attackId && it.techniqueId == techniqueId && it.energyId == energyId
        }
}
