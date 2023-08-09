package com.example.empty_project.shared.loan.core.domain.repository

interface UserRepository {

	suspend fun registerUser(name: String, password: String)

	suspend fun loginUser(name: String, password: String)
}