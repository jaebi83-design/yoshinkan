package com.yoshinkan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppScreen()
                }
            }
        }
    }
}

@Composable
fun AppScreen() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Selection) }
    var selectedAttack by remember { mutableStateOf<String?>(null) }
    var selectedTechnique by remember { mutableStateOf<String?>(null) }
    var selectedEnergy by remember { mutableStateOf<String?>(null) }

    when (currentScreen) {
        is Screen.Selection -> {
            SelectionScreen(
                onPlayClick = { attack, technique, energy ->
                    selectedAttack = attack
                    selectedTechnique = technique
                    selectedEnergy = energy
                    currentScreen = Screen.VideoPlayer(attack, technique, energy)
                }
            )
        }
        is Screen.VideoPlayer -> {
            val screen = currentScreen as Screen.VideoPlayer
            VideoPlayerScreen(
                attack = screen.attack,
                technique = screen.technique,
                energy = screen.energy,
                onBackClick = {
                    currentScreen = Screen.Selection
                }
            )
        }
    }
}

sealed class Screen {
    data object Selection : Screen()
    data class VideoPlayer(val attack: String, val technique: String, val energy: String) : Screen()
}

@Composable
fun SelectionScreen(
    onPlayClick: (attack: String, technique: String, energy: String) -> Unit
) {
    var selectedAttack by remember { mutableStateOf<String?>(null) }
    var selectedTechnique by remember { mutableStateOf<String?>(null) }
    var selectedEnergy by remember { mutableStateOf<String?>(null) }
    var attackMenuExpanded by remember { mutableStateOf(false) }
    var techniqueMenuExpanded by remember { mutableStateOf(false) }
    var energyMenuExpanded by remember { mutableStateOf(false) }

    val attacks = listOf(
        "Kata Mochi",
        "Katate Ayagyaku Mochi",
        "Katate Ayajun Mochi",
        "Katate Mochi",
        "Mune Mochi",
        "RyoKata Mochi",
        "RyoSode Mochi",
        "Ryote Mochi",
        "Shomen Uchi",
        "Sode Mochi",
        "Suigetsu Zuki",
        "Ushiro Waza Eri Mochi",
        "Ushiro Waza Katate Eri Mochi",
        "Ushiro Waza RyoHiji Mochi",
        "Ushiro Waza Ryote Mochi",
        "Yokomen Uchi"
    )

    val techniques = listOf(
        "Hiji Shime",
        "Ikkajo Osae",
        "Irimi Tsuki",
        "Nikajo Osae",
        "Sankajo Osae",
        "Yonkajo Osae",
        "Irimi Nage",
        "Kotegaeshi Osae",
        "Hiji Ate",
        "Shiho Nage",
        "Sokumen Irimi Nage",
        "TenChi Nage"
    )

    val energyFlows = listOf("Ichi", "Ni")

    val isAllSelected = selectedAttack != null && selectedTechnique != null && selectedEnergy != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Professional Martial Arts Title Header - Option 1: Compact/Minimal
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF1a1a2e),  // Dark navy blue
                            Color(0xFF0f0f1e)   // Darker navy
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                )
                .padding(top = 80.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "YOSHINKAN AIKIDO",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        letterSpacing = 2.sp
                    ),
                    color = Color(0xFFd4af37),  // Gold color
                    textAlign = TextAlign.Center
                )
                // Gold divider line
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(1.dp)
                        .background(Color(0xFFd4af37))
                        .padding(vertical = 4.dp)
                )
                Text(
                    "Tachi Waza Techniques",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Light,
                        fontSize = 13.sp,
                        letterSpacing = 1.sp
                    ),
                    color = Color(0xFFe8e8e8),  // Off-white
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Text("Select Attack:", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp))
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            Button(onClick = { attackMenuExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text(selectedAttack ?: "Choose Attack")
            }
            DropdownMenu(
                expanded = attackMenuExpanded,
                onDismissRequest = { attackMenuExpanded = false },
                modifier = Modifier.heightIn(max = 300.dp)
            ) {
                attacks.forEach { attack ->
                    DropdownMenuItem(
                        text = { Text(attack) },
                        onClick = {
                            selectedAttack = attack
                            attackMenuExpanded = false
                        }
                    )
                }
            }
        }

        Text("Select Technique:", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp))
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            Button(onClick = { techniqueMenuExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text(selectedTechnique ?: "Choose Technique")
            }
            DropdownMenu(
                expanded = techniqueMenuExpanded,
                onDismissRequest = { techniqueMenuExpanded = false },
                modifier = Modifier.heightIn(max = 300.dp)
            ) {
                techniques.forEach { technique ->
                    DropdownMenuItem(
                        text = { Text(technique) },
                        onClick = {
                            selectedTechnique = technique
                            techniqueMenuExpanded = false
                        }
                    )
                }
            }
        }

        Text("Select Energy Flow:", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp))
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            Button(onClick = { energyMenuExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text(selectedEnergy ?: "Choose Energy")
            }
            DropdownMenu(expanded = energyMenuExpanded, onDismissRequest = { energyMenuExpanded = false }) {
                energyFlows.forEach { energy ->
                    DropdownMenuItem(
                        text = { Text(energy) },
                        onClick = {
                            selectedEnergy = energy
                            energyMenuExpanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                if (selectedAttack != null && selectedTechnique != null && selectedEnergy != null) {
                    onPlayClick(selectedAttack!!, selectedTechnique!!, selectedEnergy!!)
                }
            },
            enabled = isAllSelected,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
        ) {
            Text("Play Video")
        }

        if (isAllSelected) {
            Text(
                "Attack: $selectedAttack | Technique: $selectedTechnique | Energy: $selectedEnergy",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915, name = "Phone")
@Composable
fun SelectionScreenPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SelectionScreen(
                onPlayClick = { _, _, _ -> }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 1280, name = "Tablet")
@Composable
fun SelectionScreenTabletPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SelectionScreen(
                onPlayClick = { _, _, _ -> }
            )
        }
    }
}
