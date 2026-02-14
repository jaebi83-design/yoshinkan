import java.io.File
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

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

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: kotlin VideoManifestGenerator.kt <path_to_video_directory>")
        println("Example: kotlin VideoManifestGenerator.kt \"C:\\Users\\Jim\\claude-skills\\video-decomposer\\data\\Tachi Waza - Clean\"")
        return
    }

    val videoDirectoryPath = args[0]
    println("Scanning directory: $videoDirectoryPath")

    val manifest = scanVideoDirectory(videoDirectoryPath)

    if (manifest.videos.isEmpty()) {
        println("No valid video files found!")
        return
    }

    println("Found ${manifest.videos.size} video files")

    // Save manifest to file
    val outputFile = File(videoDirectoryPath, "video_manifest.json")
    val json = Json { prettyPrint = true }
    val jsonString = json.encodeToString(manifest)

    outputFile.writeText(jsonString)
    println("Manifest saved to: ${outputFile.absolutePath}")

    // Print summary
    println("\nVideo Summary:")
    manifest.videos.forEach { video ->
        println("  - ${video.fileName}")
    }
}

fun scanVideoDirectory(directoryPath: String): VideoManifest {
    val videoDir = File(directoryPath)
    val videoEntries = mutableListOf<VideoEntry>()

    if (!videoDir.exists() || !videoDir.isDirectory) {
        println("Error: Directory does not exist or is not a directory")
        return VideoManifest(emptyList())
    }

    videoDir.listFiles()?.forEach { file ->
        if (file.isFile && file.extension == "mp4") {
            val videoEntry = parseVideoFileName(file.nameWithoutExtension)
            if (videoEntry != null) {
                videoEntries.add(videoEntry)
            } else {
                println("Warning: Could not parse filename: ${file.name}")
            }
        }
    }

    return VideoManifest(videoEntries.sortedBy { it.fileName })
}

fun parseVideoFileName(fileName: String): VideoEntry? {
    try {
        val parts = fileName.split("_")
        if (parts.size < 3) return null

        // Energy is always the last part (ICHI or NI)
        val energyPart = parts.last()
        val energyId = when (energyPart.uppercase()) {
            "ICHI" -> 1
            "NI" -> 2
            else -> return null
        }

        // Find the technique part
        val techniqueName = identifyTechniquePart(parts)
        if (techniqueName == null) return null

        val techniqueId = getTechniqueId(techniqueName)
        if (techniqueId == null) return null

        // Get attack part
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

fun identifyTechniquePart(parts: List<String>): String? {
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

    val partsWithoutEnergy = parts.dropLast(1)

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

fun getAttackId(attackName: String): Int? {
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

fun getTechniqueId(techniqueName: String): Int? {
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
