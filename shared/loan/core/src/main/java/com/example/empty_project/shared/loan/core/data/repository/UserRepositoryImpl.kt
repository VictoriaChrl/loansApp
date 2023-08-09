package com.example.empty_project.shared.loan.core.data.repository

import com.example.empty_project.shared.loan.core.data.SharPrefManagerImpl
import com.example.empty_project.shared.loan.core.data.api.LoansApi
import com.example.empty_project.shared.loan.core.data.model.AuthModel
import com.example.empty_project.shared.loan.core.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl
@Inject constructor(
    private val loansApi: LoansApi,
    private val sharPrefManager: SharPrefManagerImpl
) : UserRepository {

    override suspend fun registerUser(name: String, password: String) {
           loansApi.registerUser(AuthModel(name, password))
    }

    override suspend fun loginUser(name: String, password: String) {
        val response = loansApi.loginUser(AuthModel(name, password))

        val token = response.string()

        sharPrefManager.apply {
            saveName(name)
            savePassword(password)
            saveToken(token)
        }
    }
}