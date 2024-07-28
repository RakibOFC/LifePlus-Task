package com.rakibofc.lifeplustask.data.local

import com.rakibofc.lifeplustask.domain.usecase.AuthUseCase
import com.rakibofc.lifeplustask.util.UiState
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val lifePlusDao: LifePlusDao
) : AuthUseCase {

    override suspend fun registerUser(user: UserEntity): UiState<String> {
        return try {
            lifePlusDao.registerUser(user)
            UiState.Success("User registered successfully")
        } catch (ex: Exception) {
            UiState.Error(ex.message ?: "Unknown error occurred")
        }
    }

    override suspend fun loginUser(userName: String, password: String): UiState<UserEntity> {
        return try {
            val user = lifePlusDao.loginUser(userName, password)
            if (user != null) {
                UiState.Success(user)
            } else {
                UiState.Error("Invalid username or password")
            }
        } catch (ex: Exception) {
            UiState.Error(ex.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getUserById(userId: Long): UiState<UserEntity> {
        return try {
            val user = lifePlusDao.getUserById(userId)
            if (user != null) {
                UiState.Success(user)
            } else {
                UiState.Error("User not found")
            }
        } catch (ex: Exception) {
            UiState.Error(ex.message ?: "Unknown error occurred")
        }
    }
}