package com.example.empty_project.domain.usecase

import com.example.empty_project.domain.repository.UserRepository

class LoginUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(name: String, password: String) {
        repository.loginUser(name, password)
    }
}
