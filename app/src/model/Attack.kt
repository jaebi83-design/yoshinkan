package com.yoshinkan.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attacks")
data class Attack(
    @PrimaryKey
    val id: Int,
    val name: String,
    val displayName: String
)
