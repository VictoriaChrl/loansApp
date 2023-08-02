package com.example.empty_project.feature.loan.register.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empty_project.domain.usecase.RegistrationUseCase
import com.example.empty_project.shared.loan.core.SingleLiveEvent
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

    private val _event: SingleLiveEvent<RegistrationUiState> = SingleLiveEvent()
    val event: LiveData<RegistrationUiState> = _event

    fun registerUser(name: String, password: String) {
        _event.value = RegistrationUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                registrationUseCase(
                    name = name,
                    password = password
                )
                _event.postValue(RegistrationUiState.Complete)
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
            is NoRouteToHostException -> {
                _event.postValue(RegistrationUiState.Error.NoInternet)
            }
            else -> {
                _event.postValue(RegistrationUiState.Error.AlreadyExist)
            }
        }
    }

    override fun onCleared() {
        _event.call()
        super.onCleared()
    }
}