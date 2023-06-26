package com.example.empty_project.presentation.states

import com.example.empty_project.domain.entity.Loan

sealed interface LoanDetailsUiState {

    object Initial : LoanDetailsUiState

    object Loading : LoanDetailsUiState

    data class Complete(val loan: Loan) : LoanDetailsUiState

    sealed interface Error : LoanDetailsUiState {

        object NoInternet : Error

        object Unknown : Error
    }
}