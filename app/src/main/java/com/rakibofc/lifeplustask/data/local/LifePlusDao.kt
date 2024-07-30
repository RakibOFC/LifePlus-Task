package com.rakibofc.lifeplustask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LifePlusDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun registerUser(user: UserEntity) : Long

    @Query("SELECT userName FROM users WHERE userName = :inputUserName")
    suspend fun userNameValidation(inputUserName: String): String?

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

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Long): UserEntity?
}