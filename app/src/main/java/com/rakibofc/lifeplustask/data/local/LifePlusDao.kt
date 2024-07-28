package com.rakibofc.lifeplustask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LifePlusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(user: UserEntity)

    @Query(
        """
        SELECT
            *
        FROM
            users
        WHERE
            userName = :userName AND password = :password
    """
    )
    suspend fun loginUser(userName: String, password: String): UserEntity?
}