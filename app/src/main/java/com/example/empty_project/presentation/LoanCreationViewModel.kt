package com.example.empty_project.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empty_project.domain.entity.NewLoan
import com.example.empty_project.domain.usecase.CreateLoanUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.inject.Inject

class LoanCreationViewModel @Inject constructor(
    private val createLoanUseCase: CreateLoanUseCase
) :
    ViewModel() {

    private val _state: MutableLiveData<LoanCreationUiState> =
        MutableLiveData(LoanCreationUiState.Initial)
    val state: LiveData<LoanCreationUiState> = _state

    private val handleError = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is ConnectException,
            is UnknownHostException,
            is NoRouteToHostException -> _state.value = LoanCreationUiState.Error.NoInternet

            else -> _state.value =
                LoanCreationUiState.Error.Unknown
        }
    }

    fun createLoan(newLoan: NewLoan) {
        LoanHistoryUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            createLoanUseCase(newLoan)
        }
        LoanHistoryUiState.CompleteLogin
    }
}