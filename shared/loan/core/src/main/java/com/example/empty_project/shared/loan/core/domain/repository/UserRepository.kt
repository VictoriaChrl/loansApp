package com.example.empty_project.domain.repository

interface UserRepository {

	suspend fun registerUser(name: String, password: String)

	suspend fun loginUser(name: String, password: String)
}