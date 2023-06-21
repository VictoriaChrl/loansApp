package com.example.empty_project.presentation

sealed interface LoanCreationUiState {

    object Initial : LoanCreationUiState

    object Loading : LoanCreationUiState

    data class Complete(val message: String) : LoanCreationUiState

//    data class Error(val message: String) : AuthorizationUiState

    sealed interface Error : LoanCreationUiState {

        object NoInternet : Error

        object Unknown : Error
    }

}
