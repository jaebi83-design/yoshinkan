package com.yoshinkan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yoshinkan.database.YoshinkanDatabase
import com.yoshinkan.repository.YoshinkanRepository
import com.yoshinkan.ui.screen.SelectionScreen
import com.yoshinkan.ui.screen.VideoPlaybackScreen
import com.yoshinkan.ui.theme.YoshinkanTheme
import com.yoshinkan.utility.DataInitializer
import com.yoshinkan.viewmodel.YoshinkanViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database and repository
        val database = YoshinkanDatabase.getDatabase(this)
        val repository = YoshinkanRepository(
            database.attackDao(),
            database.techniqueDao(),
            database.energyFlowDao()
        )

        // Initialize ViewModel
        val viewModel = YoshinkanViewModel(repository)

        // Initialize database with data on first launch
        lifecycleScope.launch {
            val existingAttacks = repository.getAllAttacks()
            if (existingAttacks.isEmpty()) {
                repository.insertAttacks(DataInitializer.getInitialAttacks())
                repository.insertTechniques(DataInitializer.getInitialTechniques())
                repository.insertEnergyFlows(DataInitializer.getInitialEnergyFlows())
            }
        }

        setContent {
            YoshinkanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember { mutableStateOf("selection") }

                    when (currentScreen) {
                        "selection" -> SelectionScreen(
                            viewModel = viewModel,
                            onPlayClick = { currentScreen = "playback" }
                        )
                        "playback" -> VideoPlaybackScreen(
                            videoFileName = viewModel.getVideoFileName(),
                            onBack = { currentScreen = "selection" }
                        )
                    }
                }
            }
        }
    }
}
