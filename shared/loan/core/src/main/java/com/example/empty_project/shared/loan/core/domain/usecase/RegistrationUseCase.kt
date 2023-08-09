package com.example.empty_project.shared.loan.core.domain.usecase

import com.example.empty_project.shared.loan.core.domain.repository.UserRepository

class RegistrationUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(
        name: String, password: String
    ) {
        repository.registerUser(name, password)
    }
}