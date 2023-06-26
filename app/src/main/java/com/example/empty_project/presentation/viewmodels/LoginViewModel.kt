package com.example.empty_project.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _state: MutableLiveData<LoginUiState> =
        MutableLiveData(LoginUiState.Initial)
    val state: LiveData<LoginUiState> = _state

    fun loginUser(name: String, password: String) {
        _state.value = LoginUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loginUseCase(
                    name = name,
                    password = password
                )
                onComplete()
            } catch (exception: Exception) {
                handleException(exception)
            }
        }
    }

    private fun onComplete() {
        _state.postValue(LoginUiState.Complete)
    }

    private fun handleException(exception: Exception) {
        when (exception) {
            is ConnectException,
            is UnknownHostException,
            is NoRouteToHostException -> _state.postValue(LoginUiState.Error.NoInternet)

            else -> _state.postValue(LoginUiState.Error.Unknown)
        }
    }
}