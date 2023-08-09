package com.example.empty_project.shared.loan.core.domain.usecase

import com.example.empty_project.shared.loan.core.domain.repository.UserRepository

class LoginUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(name: String, password: String) {
        repository.loginUser(name, password)
    }
}
