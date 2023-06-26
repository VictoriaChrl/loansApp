package com.example.empty_project.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empty_project.domain.usecase.RegistrationUseCase
import com.example.empty_project.presentation.states.RegistrationUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {

    private val _state: MutableLiveData<RegistrationUiState> =
        MutableLiveData(RegistrationUiState.Initial)
    val state: LiveData<RegistrationUiState> = _state

    fun registerUser(name: String, password: String) {
        _state.value = RegistrationUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                registrationUseCase(
                    name = name,
                    password = password
                )
                _state.postValue(RegistrationUiState.Complete)
                _state.postValue(RegistrationUiState.End)
            } catch (exception: Exception) {
                handleException(exception)
            }
        }
    }

    private fun handleException(exception: Exception){
        when (exception) {
            is SSLHandshakeException,
            is ConnectException,
            is UnknownHostException,
            is NoRouteToHostException -> _state.postValue(RegistrationUiState.Error.NoInternet)

            else -> _state.postValue(RegistrationUiState.Error.AlreadyExist)
        }
    }
}