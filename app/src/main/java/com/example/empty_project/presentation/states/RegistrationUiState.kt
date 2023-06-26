package com.example.empty_project.presentation.states

sealed interface RegistrationUiState {

    object Initial : RegistrationUiState

    object Loading : RegistrationUiState

    object Complete : RegistrationUiState

    sealed interface Error : RegistrationUiState {

        object NoInternet : Error

        object AlreadyExist : Error
    }

    object End : RegistrationUiState

}
