package com.example.empty_project.presentation

import com.example.empty_project.domain.entity.LoanConditions

sealed interface LoanCreationUiState {

    object Initial : LoanCreationUiState

    object LoadingConditions : LoanCreationUiState

    data class CompleteLoadingConditions(val loanConditions: LoanConditions) : LoanCreationUiState

    object LoadingLoanCreation : LoanCreationUiState

    data class CompleteLoanCreation(val message: String) : LoanCreationUiState

    sealed interface Error : LoanCreationUiState {

        object NoInternet : Error

        data class Unknown(val message: String) : Error
    }

}
