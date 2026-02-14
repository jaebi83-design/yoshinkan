package com.yoshinkan.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "energy_flows")
data class EnergyFlow(
    @PrimaryKey
    val id: Int,
    val name: String,
    val displayName: String,
    val description: String = ""
)
