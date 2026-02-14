package com.yoshinkan.utility

import com.yoshinkan.model.VideoEntry
import com.yoshinkan.model.VideoManifest
import java.io.File

object VideoScanner {

    /**
     * Scans a directory for video files matching the naming convention
     * Pattern: {ATTACK}_{TECHNIQUE}_{ENERGY}.mp4
     *
     * Returns a VideoManifest that can be serialized to JSON
     */
    fun scanVideoDirectory(directoryPath: String): VideoManifest {
        val videoDir = File(directoryPath)
        val videoEntries = mutableListOf<VideoEntry>()

        if (!videoDir.exists() || !videoDir.isDirectory) {
            return VideoManifest(emptyList())
        }

        videoDir.listFiles()?.forEach { file ->
            if (file.isFile && file.extension == "mp4") {
                val videoEntry = parseVideoFileName(file.nameWithoutExtension)
                if (videoEntry != null) {
                    videoEntries.add(videoEntry)
                }
            }
        }

        return VideoManifest(videoEntries.sortedBy { it.fileName })
    }

    /**
     * Parses a video filename and extracts attack, technique, and energy IDs
     * Filename format: {ATTACK}_{TECHNIQUE}_{ENERGY}.mp4
     * Example: SUIGETSU_ZUKI_IKKAJO_OSAE_ICHI
     */
    private fun parseVideoFileName(fileName: String): VideoEntry? {
        try {
            val parts = fileName.split("_")
            if (parts.size < 3) return null

            // Split parts into attack, technique, and energy
            // Energy is always the last part (ICHI or NI)
            val energyPart = parts.last()
            val energyId = when (energyPart.uppercase()) {
                "ICHI" -> 1
                "NI" -> 2
                else -> return null
            }

            // Technique is the second-to-last part(s)
            // Attack is everything else
            // This is tricky since both can have multiple words

            // Strategy: Find the technique name by checking against known techniques
            val techniqueName = identifyTechniquePart(parts)
            if (techniqueName == null) return null

            val techniqueId = getTechniqueId(techniqueName)
            if (techniqueId == null) return null

            // Remove technique and energy parts to get attack
            val techniqueParts = techniqueName.split("_")
            val partsToRemove = techniqueParts.size + 1 // +1 for energy
            if (parts.size <= partsToRemove) return null

            val attackParts = parts.dropLast(partsToRemove)
            val attackName = attackParts.joinToString("_")
            val attackId = getAttackId(attackName)
            if (attackId == null) return null

            val displayName = "$attackName - $techniqueName - $energyPart"

            return VideoEntry(
                fileName = "$fileName.mp4",
                attackId = attackId,
                techniqueId = techniqueId,
                energyId = energyId,
                displayName = displayName
            )
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * Identifies the technique portion of the filename
     */
    private fun identifyTechniquePart(parts: List<String>): String? {
        // Known technique names
        val techniques = listOf(
            "HIJI_SHIME",
            "IKKAJO_OSAE",
            "IRIMI_TSUKI",
            "NIKAJO_OSAE",
            "SANKAJO_OSAE",
            "YONKAJO_OSAE",
            "IRIMI_NAGE",
            "KOTEGAESHI_OSAE",
            "HIJI_ATE",
            "SHIHO_NAGE",
            "SOKUMEN_IRIMI_NAGE",
            "TENCHI_NAGE"
        )

        // Work backwards from the energy part
        val partsWithoutEnergy = parts.dropLast(1) // Remove energy

        // Try matching techniques from longest to shortest
        for (technique in techniques.sortedByDescending { it.length }) {
            val techniqueParts = technique.split("_")
            if (partsWithoutEnergy.size >= techniqueParts.size) {
                val potentialTechnique = partsWithoutEnergy.takeLast(techniqueParts.size).joinToString("_")
                if (potentialTechnique == technique) {
                    return technique
                }
            }
        }

        return null
    }

    /**
     * Maps attack name to ID
     */
    private fun getAttackId(attackName: String): Int? {
        return when (attackName) {
            "KATA_MOCHI" -> 1
            "KATATE_AYAGYAKU_MOCHI" -> 2
            "KATATE_AYAJUN_MOCHI" -> 3
            "KATATE_MOCHI" -> 4
            "MUNE_MOCHI" -> 5
            "RYOKATA_MOCHI" -> 6
            "RYOSODE_MOCHI" -> 7
            "RYOTE_MOCHI" -> 8
            "SHOMEN_UCHI" -> 9
            "SODE_MOCHI" -> 10
            "SUIGETSU_ZUKI" -> 11
            "USHIRO_WAZA_ERI_MOCHI" -> 12
            "USHIRO_WAZA_KATATE_ERI_MOCHI" -> 13
            "USHIRO_WAZA_RYOHIJI_MOCHI" -> 14
            "USHIRO_WAZA_RYOTE_MOCHI" -> 15
            "YOKOMEN_UCHI" -> 16
            else -> null
        }
    }

    /**
     * Maps technique name to ID
     */
    private fun getTechniqueId(techniqueName: String): Int? {
        return when (techniqueName) {
            "HIJI_SHIME" -> 1
            "IKKAJO_OSAE" -> 2
            "IRIMI_TSUKI" -> 3
            "NIKAJO_OSAE" -> 4
            "SANKAJO_OSAE" -> 5
            "YONKAJO_OSAE" -> 6
            "IRIMI_NAGE" -> 7
            "KOTEGAESHI_OSAE" -> 8
            "HIJI_ATE" -> 9
            "SHIHO_NAGE" -> 10
            "SOKUMEN_IRIMI_NAGE" -> 11
            "TENCHI_NAGE" -> 12
            else -> null
        }
    }
}
