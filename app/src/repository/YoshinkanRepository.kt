package com.yoshinkan.repository

import com.yoshinkan.database.AttackDao
import com.yoshinkan.database.TechniqueDao
import com.yoshinkan.database.EnergyFlowDao
import com.yoshinkan.model.Attack
import com.yoshinkan.model.Technique
import com.yoshinkan.model.EnergyFlow

class YoshinkanRepository(
    private val attackDao: AttackDao,
    private val techniqueDao: TechniqueDao,
    private val energyFlowDao: EnergyFlowDao
) {
    suspend fun getAllAttacks(): List<Attack> {
        return attackDao.getAllAttacks()
    }

    suspend fun getAllTechniques(): List<Technique> {
        return techniqueDao.getAllTechniques()
    }

    suspend fun getAllEnergyFlows(): List<EnergyFlow> {
        return energyFlowDao.getAllEnergyFlows()
    }

    suspend fun getAttackById(id: Int): Attack? {
        return attackDao.getAttackById(id)
    }

    suspend fun getTechniqueById(id: Int): Technique? {
        return techniqueDao.getTechniqueById(id)
    }

    suspend fun getEnergyFlowById(id: Int): EnergyFlow? {
        return energyFlowDao.getEnergyFlowById(id)
    }

    suspend fun insertAttacks(attacks: List<Attack>) {
        attackDao.insertAttacks(attacks)
    }

    suspend fun insertTechniques(techniques: List<Technique>) {
        techniqueDao.insertTechniques(techniques)
    }

    suspend fun insertEnergyFlows(energyFlows: List<EnergyFlow>) {
        energyFlowDao.insertEnergyFlows(energyFlows)
    }
}
