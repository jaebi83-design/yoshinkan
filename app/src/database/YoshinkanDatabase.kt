package com.yoshinkan.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yoshinkan.model.Attack
import com.yoshinkan.model.Technique
import com.yoshinkan.model.EnergyFlow

@Database(
    entities = [Attack::class, Technique::class, EnergyFlow::class],
    version = 1,
    exportSchema = false
)
abstract class YoshinkanDatabase : RoomDatabase() {
    abstract fun attackDao(): AttackDao
    abstract fun techniqueDao(): TechniqueDao
    abstract fun energyFlowDao(): EnergyFlowDao

    companion object {
        @Volatile
        private var INSTANCE: YoshinkanDatabase? = null

        fun getDatabase(context: Context): YoshinkanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    YoshinkanDatabase::class.java,
                    "yoshinkan_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
