package com.example.empty_project.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empty_project.domain.entity.NewLoan
import com.example.empty_project.domain.usecase.CreateLoanUseCase
import com.example.empty_project.domain.usecase.GetLoanConditionsUseCase
import com.example.empty_project.presentation.states.LoanCreationUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class LoanCreationViewModel @Inject constructor(
    private val createLoanUseCase: CreateLoanUseCase,
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase
) : ViewModel() {

    private val _state:  MutableLiveData<LoanCreationUiState> =
        MutableLiveData(LoanCreationUiState.Initial)
    val state: LiveData<LoanCreationUiState> = _state
    private val _event: SingleLiveEvent<LoanCreationUiState> = SingleLiveEvent()
    val event: LiveData<LoanCreationUiState> = _event

    fun getLoanConditions() {
        _state.value = LoanCreationUiState.LoadingConditions
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(LoanCreationUiState.CompleteLoadingConditions(getLoanConditionsUseCase()))
            } catch (exception: Exception) {
                handleException(exception)
            }
        }
    }

    private fun handleException(exception: Exception) {
        when (exception) {
            is SSLHandshakeException,
            is ConnectException,
            is UnknownHostException,
            is NoRouteToHostException -> _event.postValue(LoanCreationUiState.Error.NoInternet)

            else -> _event.postValue(LoanCreationUiState.Error.Unknown)
        }
    }

    fun createLoan(newLoan: NewLoan) {
        _event.value = LoanCreationUiState.LoadingLoanCreation
        viewModelScope.launch(Dispatchers.IO) {
            try {
                createLoanUseCase(newLoan)
                onCompleteLoanCreation()
            } catch (exception: Exception) {
                handleException(exception)
            }
        }
    }

    private fun onCompleteLoanCreation() {
        _event.postValue(LoanCreationUiState.CompleteLoanCreation)
    }

}