package com.rakibofc.lifeplustask.domain.usecase

import com.rakibofc.lifeplustask.data.local.UserEntity
import com.rakibofc.lifeplustask.data.remote.SearchResult
import com.rakibofc.lifeplustask.data.remote.Show
import com.rakibofc.lifeplustask.util.UiState

interface MainUseCase {
    suspend fun registerUser(user: UserEntity): UiState<String>
    suspend fun loginUser(userName: String, password: String): UiState<UserEntity>
    suspend fun getUserById(userId: Long): UiState<UserEntity>
    suspend fun getSearchResult(query: String): UiState<List<SearchResult>>
    suspend fun setShowDetails(showDetails: Show?): UiState<Show>
}