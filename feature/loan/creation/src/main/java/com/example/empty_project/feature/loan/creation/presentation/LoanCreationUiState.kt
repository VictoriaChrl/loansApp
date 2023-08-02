package com.example.empty_project.feature.loan.creation.presentation

import com.example.empty_project.domain.entity.LoanConditions

sealed interface LoanCreationUiState {

    object Initial : LoanCreationUiState

    object LoadingConditions : LoanCreationUiState

    data class CompleteLoadingConditions(val loanConditions: LoanConditions) : LoanCreationUiState

    object LoadingLoanCreation : LoanCreationUiState

    object CompleteLoanCreation: LoanCreationUiState

    sealed interface Error : LoanCreationUiState {

        object NoInternet : Error

        object Unknown : Error
    }

}
