package com.rakibofc.lifeplustask.data.repository

import com.rakibofc.lifeplustask.data.local.LifePlusDao
import com.rakibofc.lifeplustask.data.local.UserEntity
import com.rakibofc.lifeplustask.data.remote.ApiService
import com.rakibofc.lifeplustask.data.remote.SearchResult
import com.rakibofc.lifeplustask.domain.usecase.MainUseCase
import com.rakibofc.lifeplustask.util.UiState
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val lifePlusDao: LifePlusDao,
    private val apiService: ApiService
) : MainUseCase {

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

    override suspend fun getSearchResult(query: String): UiState<List<SearchResult>> {
        return try {
            UiState.Success(apiService.getSearchResult(query))
        } catch (ex: Exception) {
            UiState.Error(ex.message ?: "Unknown error occurred")
        }
    }
}