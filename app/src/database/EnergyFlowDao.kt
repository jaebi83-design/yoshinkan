package com.yoshinkan.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoshinkan.model.EnergyFlow

@Dao
interface EnergyFlowDao {
    @Query("SELECT * FROM energy_flows ORDER BY id")
    suspend fun getAllEnergyFlows(): List<EnergyFlow>

    @Query("SELECT * FROM energy_flows WHERE id = :id")
    suspend fun getEnergyFlowById(id: Int): EnergyFlow?

    @Insert
    suspend fun insertEnergyFlow(energyFlow: EnergyFlow)

    @Insert
    suspend fun insertEnergyFlows(energyFlows: List<EnergyFlow>)

    @Query("DELETE FROM energy_flows")
    suspend fun deleteAllEnergyFlows()
}
