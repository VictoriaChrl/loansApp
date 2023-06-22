package com.example.empty_project.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empty_project.domain.usecase.RegistrationUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {

    private val _state: MutableLiveData<AuthorizationUiState> =
        MutableLiveData(AuthorizationUiState.Initial)
    val state: LiveData<AuthorizationUiState> = _state

    fun registerUser(name: String, password: String) {
        _state.value = AuthorizationUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                registrationUseCase(
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
        _state.postValue(AuthorizationUiState.Complete("Регистрация прошла успешно!"))
    }

    private fun handleException(exception: Exception){
        when (exception) {
            is ConnectException,
            is UnknownHostException,
            is NoRouteToHostException -> _state.postValue(AuthorizationUiState.Error.NoInternet)

            else -> _state.postValue(AuthorizationUiState.Error.AlreadyExist)
        }
    }
}