package com.yoshinkan.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "techniques")
data class Technique(
    @PrimaryKey
    val id: Int,
    val name: String,
    val displayName: String
)
