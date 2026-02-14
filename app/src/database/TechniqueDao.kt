package com.yoshinkan.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoshinkan.model.Technique

@Dao
interface TechniqueDao {
    @Query("SELECT * FROM techniques ORDER BY id")
    suspend fun getAllTechniques(): List<Technique>

    @Query("SELECT * FROM techniques WHERE id = :id")
    suspend fun getTechniqueById(id: Int): Technique?

    @Insert
    suspend fun insertTechnique(technique: Technique)

    @Insert
    suspend fun insertTechniques(techniques: List<Technique>)

    @Query("DELETE FROM techniques")
    suspend fun deleteAllTechniques()
}
