package com.example.empty_project.feature.loan.history.presentation

import com.example.empty_project.shared.loan.core.domain.entity.Loan

sealed interface LoanHistoryUiState {

    object Initial : LoanHistoryUiState

    object Loading : LoanHistoryUiState

    data class Complete(val list: List<Loan>) : LoanHistoryUiState

    sealed interface Error : LoanHistoryUiState {

        object NoInternet : Error

        object Unknown : Error
    }

}
