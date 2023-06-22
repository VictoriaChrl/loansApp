package com.example.empty_project.presentation

sealed interface AuthorizationUiState {

    object Initial : AuthorizationUiState

    object Loading : AuthorizationUiState

    data class Complete(val message: String) : AuthorizationUiState

    sealed interface Error : AuthorizationUiState {

        object NoInternet : Error

        object AlreadyExist : Error
    }

}
