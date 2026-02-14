package com.yoshinkan.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.PaddingValues
import com.yoshinkan.model.Attack
import com.yoshinkan.model.Technique
import com.yoshinkan.model.EnergyFlow
import com.yoshinkan.viewmodel.YoshinkanViewModel

@Composable
fun SelectionScreen(viewModel: YoshinkanViewModel, onPlayClick: () -> Unit = {}) {
    val attacks by viewModel.attacks.collectAsState()
    val techniques by viewModel.techniques.collectAsState()
    val energyFlows by viewModel.energyFlows.collectAsState()
    val selection by viewModel.selection.collectAsState()

    var selectedAttackIndex by remember { mutableStateOf(0) }
    var selectedTechniqueIndex by remember { mutableStateOf(0) }
    var selectedEnergyIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Yoshinkan",
            style = MaterialTheme.typography.headlineLarge
        )

        // Attack Selection
        if (attacks.isNotEmpty()) {
            AttackDropdown(
                attacks = attacks,
                selectedIndex = selectedAttackIndex,
                onSelectionChange = { index ->
                    selectedAttackIndex = index
                    viewModel.selectAttack(attacks[index])
                }
            )
        }

        // Technique Selection
        if (techniques.isNotEmpty()) {
            TechniqueDropdown(
                techniques = techniques,
                selectedIndex = selectedTechniqueIndex,
                onSelectionChange = { index ->
                    selectedTechniqueIndex = index
                    viewModel.selectTechnique(techniques[index])
                }
            )
        }

        // Energy Flow Selection
        if (energyFlows.isNotEmpty()) {
            EnergyFlowDropdown(
                energyFlows = energyFlows,
                selectedIndex = selectedEnergyIndex,
                onSelectionChange = { index ->
                    selectedEnergyIndex = index
                    viewModel.selectEnergyFlow(energyFlows[index])
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Play Button
        Button(
            onClick = { onPlayClick() },
            enabled = selection.isComplete(),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Play Demonstration")
        }

        // Reset Button
        Button(
            onClick = { viewModel.resetSelection() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Reset")
        }
    }
}

@Composable
fun AttackDropdown(
    attacks: List<Attack>,
    selectedIndex: Int,
    onSelectionChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text(if (attacks.isNotEmpty()) attacks[selectedIndex].displayName else "Select Attack")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            attacks.forEachIndexed { index, attack ->
                DropdownMenuItem(
                    text = { Text(attack.displayName) },
                    onClick = {
                        onSelectionChange(index)
                        expanded = false
                    },
                    contentPadding = PaddingValues(0.dp)
                )
            }
        }
    }
}

@Composable
fun TechniqueDropdown(
    techniques: List<Technique>,
    selectedIndex: Int,
    onSelectionChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text(if (techniques.isNotEmpty()) techniques[selectedIndex].displayName else "Select Technique")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            techniques.forEachIndexed { index, technique ->
                DropdownMenuItem(
                    text = { Text(technique.displayName) },
                    onClick = {
                        onSelectionChange(index)
                        expanded = false
                    },
                    contentPadding = PaddingValues(0.dp)
                )
            }
        }
    }
}

@Composable
fun EnergyFlowDropdown(
    energyFlows: List<EnergyFlow>,
    selectedIndex: Int,
    onSelectionChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text(if (energyFlows.isNotEmpty()) energyFlows[selectedIndex].displayName else "Select Energy")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            energyFlows.forEachIndexed { index, energyFlow ->
                DropdownMenuItem(
                    text = { Text(energyFlow.displayName) },
                    onClick = {
                        onSelectionChange(index)
                        expanded = false
                    },
                    contentPadding = PaddingValues(0.dp)
                )
            }
        }
    }
}
