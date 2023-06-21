package com.example.empty_project.data.repository

import com.example.empty_project.data.SharPrefManagerImpl
import com.example.empty_project.data.api.LoansApi
import com.example.empty_project.data.model.AuthModel
import com.example.empty_project.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl
@Inject constructor(
    private val loansApi: LoansApi,
    private val sharPrefManager: SharPrefManagerImpl
) : UserRepository {

    override suspend fun registerUser(name: String, password: String) {
        loansApi.registerUser(AuthModel(name, password))

        sharPrefManager.apply {
            saveName(name)
            savePassword(password)
        }
    }

    override suspend fun loginUser() {
        val name = sharPrefManager.getName()
        val password = sharPrefManager.getPassword()

        val response = loansApi.loginUser(AuthModel(name, password))

        val token = response.string()

        sharPrefManager.saveToken(token)
    }
}