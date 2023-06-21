package com.example.empty_project.domain.usecase

import com.example.empty_project.domain.repository.UserRepository

class RegistrationUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(
        name: String, password: String
    ) {
        repository.registerUser(name, password)
    }
}