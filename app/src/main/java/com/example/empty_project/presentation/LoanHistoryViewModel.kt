package com.example.empty_project.presentation

import androidx.lifecycle.*
import com.example.empty_project.domain.usecase.LoginUseCase
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

    fun login() {
        _state.value = LoanHistoryUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
           try{
               loginUseCase()
               _state.postValue(LoanHistoryUiState.CompleteLogin)
           }catch (exception: Exception){
               handleException(exception)
           }
        }
    }

    private fun handleException(exception: Exception){
        when (exception) {
            is ConnectException,
            is UnknownHostException,
            is NoRouteToHostException -> _state.postValue(LoanHistoryUiState.Error.NoInternet)

            else -> _state.postValue(LoanHistoryUiState.Error.Unknown(exception.message.toString()))
        }
    }
}