package com.example.empty_project.presentation

sealed interface LoginUiState{

    object Initial : LoginUiState

    object Loading : LoginUiState

    object Complete : LoginUiState

    sealed interface Error : LoginUiState {

        object NoInternet : Error

        object Unknown : Error
    }

}