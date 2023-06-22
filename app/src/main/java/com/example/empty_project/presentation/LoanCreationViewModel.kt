package com.example.empty_project.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empty_project.domain.entity.NewLoan
import com.example.empty_project.domain.usecase.CreateLoanUseCase
import com.example.empty_project.domain.usecase.GetLoanConditionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.inject.Inject

class LoanCreationViewModel @Inject constructor(
    private val createLoanUseCase: CreateLoanUseCase,
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase
) : ViewModel() {

    private val _state: MutableLiveData<LoanCreationUiState> =
        MutableLiveData(LoanCreationUiState.Initial)
    val state: LiveData<LoanCreationUiState> = _state

    fun createLoan(newLoan: NewLoan) {
        _state.value = LoanCreationUiState.LoadingLoanCreation
        viewModelScope.launch(Dispatchers.IO) {
            try {
                createLoanUseCase(newLoan)
                onCompleteLoanCreation()
            } catch (exception: Exception) {
                handleException(exception)
            }
        }
    }

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

    private fun onCompleteLoanCreation() {
        _state.postValue(LoanCreationUiState.CompleteLoanCreation("Займ создан!"))
    }

    private fun handleException(exception: Exception) {
        when (exception) {
            is ConnectException,
            is UnknownHostException,
            is NoRouteToHostException -> _state.postValue(LoanCreationUiState.Error.NoInternet)

            else -> _state.postValue(LoanCreationUiState.Error.Unknown(exception.message.toString()))
        }
    }
}