package com.example.empty_project.feature.loan.history.presentation

import androidx.lifecycle.*
import com.example.empty_project.shared.loan.core.domain.usecase.GetAllLoansUseCase
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