package com.example.empty_project.presentation.viewmodels

import androidx.lifecycle.*
import com.example.empty_project.domain.usecase.GetAllLoansUseCase
import com.example.empty_project.presentation.states.LoanHistoryUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class LoanHistoryViewModel @Inject constructor(
    private val getAllLoansUseCase: GetAllLoansUseCase
) : ViewModel() {

    private val _state: MutableLiveData<LoanHistoryUiState> =
        MutableLiveData(LoanHistoryUiState.Initial)
    val state: LiveData<LoanHistoryUiState> = _state

    fun getLoans() {
        _state.value = LoanHistoryUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _state.postValue(LoanHistoryUiState.Complete(getAllLoansUseCase()))
            }catch (exception: Exception){
                handleException(exception)
            }
        }
    }

    private fun handleException(exception: Exception){
        when (exception) {
            is SSLHandshakeException,
            is ConnectException,
            is UnknownHostException,
            is NoRouteToHostException -> _state.postValue(LoanHistoryUiState.Error.NoInternet)

            else -> _state.postValue(LoanHistoryUiState.Error.Unknown)
        }
    }
}