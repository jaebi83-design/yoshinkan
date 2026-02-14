# Database Setup Guide

## Overview
Yoshinkan uses **Room** (SQLite) for local data persistence. The database stores:
- Attack types
- Technique names
- Energy flow definitions

## Database Structure

### Attacks Table
```sql
CREATE TABLE attacks (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    displayName TEXT NOT NULL
)
```

### Techniques Table
```sql
CREATE TABLE techniques (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    displayName TEXT NOT NULL
)
```

### Energy Flows Table
```sql
CREATE TABLE energy_flows (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    displayName TEXT NOT NULL,
    description TEXT
)
```

## Initial Data Population

### Option 1: Programmatic Population (Recommended for First Launch)
Add this code to `MainActivity.onCreate()` to populate database on first run:

```kotlin
viewModelScope.launch {
    val existingAttacks = repository.getAllAttacks()
    if (existingAttacks.isEmpty()) {
        // Load attacks from data/attacks.json and insert
        val attacks = listOf(
            Attack(1, "Kata Mochi", "Kata Mochi"),
            Attack(2, "Katate Ayagyaku Mochi", "Katate Ayagyaku Mochi"),
            // ... more attacks
        )
        repository.insertAttacks(attacks)

        // Load techniques
        val techniques = listOf(
            Technique(1, "Hiji Shime", "Hiji Shime"),
            Technique(2, "Ikkajo Osae", "Ikkajo Osae"),
            // ... more techniques
        )
        repository.insertTechniques(techniques)

        // Load energy flows
        val energyFlows = listOf(
            EnergyFlow(1, "Ichi", "Ichi"),
            EnergyFlow(2, "Ni", "Ni")
        )
        repository.insertEnergyFlows(energyFlows)
    }
}
```

### Option 2: JSON File Loading
Create a data loader utility:

```kotlin
// DataLoader.kt
object DataLoader {
    fun loadAttacksFromJson(context: Context): List<Attack> {
        val jsonString = context.assets.open("attacks.json").bufferedReader().use { it.readText() }
        // Parse JSON and return attacks list
        return emptyList()
    }

    // Similar functions for techniques and energy flows
}
```

## Database Queries

### Get All Attacks
```kotlin
val attacks = repository.getAllAttacks()
```

### Get All Techniques
```kotlin
val techniques = repository.getAllTechniques()
```

### Get All Energy Flows
```kotlin
val energyFlows = repository.getAllEnergyFlows()
```

### Get Specific Item
```kotlin
val attack = repository.getAttackById(1)
```

## Updating Database

### Add New Attack
```kotlin
val newAttack = Attack(id = 17, name = "New Attack", displayName = "New Attack")
repository.insertAttack(newAttack)
```

### Delete All Data
```kotlin
repository.attackDao().deleteAllAttacks()
repository.techniqueDao().deleteAllTechniques()
repository.energyFlowDao().deleteAllEnergyFlows()
```

## Database Migration

When making schema changes in future versions:

1. Update entity classes (Attack.kt, Technique.kt, EnergyFlow.kt)
2. Increment database version in `YoshinkanDatabase.kt`
3. Create migration class:

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Migration SQL here
    }
}
```

4. Add migration to database builder:
```kotlin
Room.databaseBuilder(
    context,
    YoshinkanDatabase::class.java,
    "yoshinkan_database"
).addMigrations(MIGRATION_1_2).build()
```

## Data Persistence Location
Room database is stored in:
- **Path**: `/data/data/com.yoshinkan/databases/yoshinkan_database`
- **Accessible**: Only by the app itself
- **Backup**: Enabled by default

## Database Debugging

To inspect database in Android Studio:
1. Connect device or emulator
2. Open Device File Explorer (View → Device File Explorer)
3. Navigate to: `data/data/com.yoshinkan/databases/`
4. Download `yoshinkan_database` file
5. Open with SQLite browser tool

## Performance Considerations
- All database operations use suspend functions (Coroutines)
- Data is loaded asynchronously to prevent UI blocking
- Consider caching frequently accessed data in ViewModel
- Use proper indices for better query performance

## Backup & Restore
Room databases are automatically backed up when:
- Device backup is enabled
- App uses AndroidX Backup

Users can restore data by:
- Uninstalling and reinstalling app
- Device backup is automatically restored
