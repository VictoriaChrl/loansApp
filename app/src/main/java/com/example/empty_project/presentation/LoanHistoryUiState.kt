package com.example.empty_project.presentation

sealed interface LoanHistoryUiState {

    object Initial : LoanHistoryUiState

    object Loading : LoanHistoryUiState

    object CompleteLogin : LoanHistoryUiState

    sealed interface Error : LoanHistoryUiState {

        object NoInternet : Error

        data class Unknown(val message: String) : Error
    }

}
