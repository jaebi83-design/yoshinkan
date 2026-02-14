package com.yoshinkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.yoshinkan.model.Attack
import com.yoshinkan.model.Technique
import com.yoshinkan.model.EnergyFlow
import com.yoshinkan.model.DemonstrationSelection
import com.yoshinkan.repository.YoshinkanRepository

class YoshinkanViewModel(private val repository: YoshinkanRepository) : ViewModel() {

    private val _attacks = MutableStateFlow<List<Attack>>(emptyList())
    val attacks: StateFlow<List<Attack>> = _attacks

    private val _techniques = MutableStateFlow<List<Technique>>(emptyList())
    val techniques: StateFlow<List<Technique>> = _techniques

    private val _energyFlows = MutableStateFlow<List<EnergyFlow>>(emptyList())
    val energyFlows: StateFlow<List<EnergyFlow>> = _energyFlows

    private val _selection = MutableStateFlow<DemonstrationSelection>(DemonstrationSelection())
    val selection: StateFlow<DemonstrationSelection> = _selection

    init {
        loadAllData()
    }

    private fun loadAllData() {
        viewModelScope.launch {
            try {
                _attacks.value = repository.getAllAttacks()
                _techniques.value = repository.getAllTechniques()
                _energyFlows.value = repository.getAllEnergyFlows()
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }

    fun selectAttack(attack: Attack) {
        _selection.value = _selection.value.copy(attack = attack)
    }

    fun selectTechnique(technique: Technique) {
        _selection.value = _selection.value.copy(technique = technique)
    }

    fun selectEnergyFlow(energyFlow: EnergyFlow) {
        _selection.value = _selection.value.copy(energyFlow = energyFlow)
    }

    fun getVideoFileName(): String? {
        return _selection.value.getVideoFileName()
    }

    fun resetSelection() {
        _selection.value = DemonstrationSelection()
    }
}
