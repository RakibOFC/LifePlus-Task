package com.rakibofc.lifeplustask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class LifePlusDatabase : RoomDatabase() {
    abstract fun lifePlusDao(): LifePlusDao
}