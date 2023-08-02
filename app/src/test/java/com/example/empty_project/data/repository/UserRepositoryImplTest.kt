package com.example.empty_project.data.repository

import com.example.empty_project.data.SharPrefManagerImpl
import com.example.empty_project.data.api.LoansApi
import com.example.empty_project.data.model.AuthModel
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


@ExtendWith(MockitoExtension::class)
class UserRepositoryImplTest {

    private val loansApi: LoansApi = mock()
    private val sharPrefManager: SharPrefManagerImpl = mock()
    private val userRepository = UserRepositoryImpl(loansApi,sharPrefManager)


    @Test
    fun `registerUser EXPECT loansApi registerUser is called`() = runTest {
        val name = "John"
        val password = "password"

        userRepository.registerUser(name, password)

        verify(loansApi).registerUser(AuthModel(name, password))
    }

    @Test
    fun `loginUser EXPECT loansApi loginUser and sharPrefManager save methods are called`() = runTest {
        val name = "John"
        val password = "password"
        val token = "token"

        val responseBody = token.toResponseBody(null)

        `when`(loansApi.loginUser(AuthModel(name, password))).thenReturn(responseBody)

        userRepository.loginUser(name, password)

        verify(loansApi).loginUser(AuthModel(name, password))

        verify(sharPrefManager).saveName(name)

        verify(sharPrefManager).savePassword(password)

        verify(sharPrefManager).saveToken(token)
    }
}