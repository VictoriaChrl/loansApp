package com.example.empty_project.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empty_project.domain.usecase.LoginUseCase
import com.example.empty_project.presentation.states.LoginUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _event: SingleLiveEvent<LoginUiState> = SingleLiveEvent()
    val event: LiveData<LoginUiState> = _event

    fun loginUser(name: String, password: String) {
        _event.value = LoginUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loginUseCase(
                    name = name,
                    password = password
                )
                _event.postValue(LoginUiState.Complete)
            } catch (exception: Exception) {
                handleException(exception)
            }
        }
    }

    private fun handleException(exception: Exception) {
        when (exception) {
            is ConnectException,
            is UnknownHostException,
            is NoRouteToHostException -> _event.postValue(LoginUiState.Error.NoInternet)

            else -> _event.postValue(LoginUiState.Error.Unknown)
        }
    }


    override fun onCleared() {
        _event.call()
        super.onCleared()
    }
}