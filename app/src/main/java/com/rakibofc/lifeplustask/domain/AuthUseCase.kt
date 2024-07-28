package com.rakibofc.lifeplustask.domain

import com.rakibofc.lifeplustask.data.local.UserEntity
import com.rakibofc.lifeplustask.util.UiState

interface AuthUseCase {
    suspend fun registerUser(user: UserEntity): UiState<String>
    suspend fun loginUser(userName: String, password: String): UiState<UserEntity>
}