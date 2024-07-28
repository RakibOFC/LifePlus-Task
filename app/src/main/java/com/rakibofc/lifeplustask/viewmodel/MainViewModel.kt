package com.rakibofc.lifeplustask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rakibofc.lifeplustask.data.local.UserEntity
import com.rakibofc.lifeplustask.domain.usecase.MainUseCase
import com.rakibofc.lifeplustask.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    private val _registerUser = MutableLiveData<UiState<String>>()
    val registerUser: LiveData<UiState<String>> get() = _registerUser

    private val _loginUser = MutableLiveData<UiState<UserEntity>>()
    val loginUser: LiveData<UiState<UserEntity>> get() = _loginUser

    private val _user = MutableLiveData<UiState<UserEntity>>()
    val user: LiveData<UiState<UserEntity>> get() = _user

    suspend fun registerUser(user: UserEntity) {
        _registerUser.postValue(UiState.Loading)
        viewModelScope.launch {
            _registerUser.postValue(mainUseCase.registerUser(user))
        }
    }

    suspend fun loginUser(userName: String, password: String) {
        _loginUser.postValue(UiState.Loading)
        viewModelScope.launch {
            _loginUser.postValue(mainUseCase.loginUser(userName, password))
        }
    }

    suspend fun loadUser(userId: Long) {
        _user.postValue(UiState.Loading)
        viewModelScope.launch {
            _user.postValue(mainUseCase.getUserById(userId))
        }
    }
}