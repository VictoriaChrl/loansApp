package com.example.empty_project.presentation.states

import com.example.empty_project.domain.entity.Loan

sealed interface LoanHistoryUiState {

    object Initial : LoanHistoryUiState

    object Loading : LoanHistoryUiState

    data class Complete(val list: List<Loan>) : LoanHistoryUiState

    sealed interface Error : LoanHistoryUiState {

        object NoInternet : Error

        object Unknown : Error
    }

}
