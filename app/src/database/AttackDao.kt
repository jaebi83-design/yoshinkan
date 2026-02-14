package com.yoshinkan.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoshinkan.model.Attack

@Dao
interface AttackDao {
    @Query("SELECT * FROM attacks ORDER BY id")
    suspend fun getAllAttacks(): List<Attack>

    @Query("SELECT * FROM attacks WHERE id = :id")
    suspend fun getAttackById(id: Int): Attack?

    @Insert
    suspend fun insertAttack(attack: Attack)

    @Insert
    suspend fun insertAttacks(attacks: List<Attack>)

    @Query("DELETE FROM attacks")
    suspend fun deleteAllAttacks()
}
