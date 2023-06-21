package com.example.empty_project.presentation

import androidx.lifecycle.*
import com.example.empty_project.domain.usecase.LoginUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.inject.Inject

class LoanHistoryViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state: MutableLiveData<LoanHistoryUiState> =
        MutableLiveData(LoanHistoryUiState.Initial)
    val state: LiveData<LoanHistoryUiState> = _state

    private val handleError = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is ConnectException,
            is UnknownHostException,
            is NoRouteToHostException -> _state.value = LoanHistoryUiState.Error.NoInternet

            else -> _state.value =
                LoanHistoryUiState.Error.Unknown(exception.message ?: exception.toString())
        }
    }

    fun login() {
        LoanHistoryUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase()
        }
        LoanHistoryUiState.CompleteLogin
    }
}