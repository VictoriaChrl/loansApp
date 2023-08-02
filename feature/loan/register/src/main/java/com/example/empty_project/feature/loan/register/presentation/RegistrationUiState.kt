package com.example.empty_project.feature.loan.register.presentation

sealed interface RegistrationUiState {

    object Initial : RegistrationUiState

    object Loading : RegistrationUiState

    object Complete : RegistrationUiState

    sealed interface Error : RegistrationUiState {

        object NoInternet : Error

        object AlreadyExist : Error
    }
}
