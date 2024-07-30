package com.rakibofc.lifeplustask.data.repository

import com.rakibofc.lifeplustask.data.local.LifePlusDao
import com.rakibofc.lifeplustask.data.local.UserEntity
import com.rakibofc.lifeplustask.data.remote.ApiService
import com.rakibofc.lifeplustask.data.remote.SearchResult
import com.rakibofc.lifeplustask.data.remote.Show
import com.rakibofc.lifeplustask.domain.usecase.MainUseCase
import com.rakibofc.lifeplustask.util.UiState
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val lifePlusDao: LifePlusDao,
    private val apiService: ApiService
) : MainUseCase {

    override suspend fun userNameValidation(inputUserName: String): UiState<String> {
        if (inputUserName.isBlank()) {
            return UiState.Error("User name cannot be empty")
        }

        return try {
            val userName = lifePlusDao.userNameValidation(inputUserName)
            if (userName?.isNotBlank() == true) {
                UiState.Error("User name already exists")
            } else {
                UiState.Success("User name is available")
            }
        } catch (ex: Exception) {
            UiState.Error(ex.message ?: "Unknown error occurred")
        }
    }

    override suspend fun registerUser(user: UserEntity): UiState<String> {
        return try {
            val userId = lifePlusDao.registerUser(user)
            when {
                userId.toFloat() == -1F -> UiState.Error("User already exists")
                else -> UiState.Success("User registered successfully")
            }
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

    override suspend fun setShowDetails(showDetails: Show?): UiState<Show> {
        return if (showDetails != null) {
            UiState.Success(showDetails)
        } else {
            UiState.Error("Data not found")
        }
    }
}