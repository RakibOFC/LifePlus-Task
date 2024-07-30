package com.rakibofc.lifeplustask.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users", indices = [Index(value = ["userName"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val userName: String,
    val password: String,
    val phone: String
) {
    companion object {
        const val USER_ID = "userId"
    }
}
