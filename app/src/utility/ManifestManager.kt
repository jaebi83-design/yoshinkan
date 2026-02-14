package com.yoshinkan.utility

import android.content.Context
import com.yoshinkan.model.VideoManifest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.serializer
import java.io.File

object ManifestManager {

    private const val MANIFEST_FILE_NAME = "video_manifest.json"

    /**
     * Loads the video manifest from the app's files directory
     */
    fun loadManifest(context: Context): VideoManifest? {
        return try {
            val manifestFile = File(context.filesDir, MANIFEST_FILE_NAME)
            if (!manifestFile.exists()) {
                return null
            }

            val jsonString = manifestFile.readText()
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString(VideoManifest.serializer(), jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Saves the video manifest to the app's files directory
     */
    fun saveManifest(context: Context, manifest: VideoManifest): Boolean {
        return try {
            val manifestFile = File(context.filesDir, MANIFEST_FILE_NAME)
            val json = Json { prettyPrint = true }
            val jsonString = json.encodeToString(VideoManifest.serializer(), manifest)
            manifestFile.writeText(jsonString)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Generates manifest from video directory
     */
    fun generateManifestFromDirectory(directoryPath: String): VideoManifest {
        return VideoScanner.scanVideoDirectory(directoryPath)
    }

    /**
     * Exports manifest to external file for backup or transfer
     */
    fun exportManifest(context: Context, outputPath: String): Boolean {
        return try {
            val manifestFile = File(context.filesDir, MANIFEST_FILE_NAME)
            if (!manifestFile.exists()) {
                return false
            }

            val outputFile = File(outputPath)
            manifestFile.copyTo(outputFile, overwrite = true)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Imports manifest from external file
     */
    fun importManifest(context: Context, inputPath: String): Boolean {
        return try {
            val inputFile = File(inputPath)
            if (!inputFile.exists()) {
                return false
            }

            val manifestFile = File(context.filesDir, MANIFEST_FILE_NAME)
            inputFile.copyTo(manifestFile, overwrite = true)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
