package com.example.empty_project.di

import com.example.empty_project.domain.repository.LoanRepository
import com.example.empty_project.domain.repository.UserRepository
import com.example.empty_project.domain.usecase.CreateLoanUseCase
import com.example.empty_project.domain.usecase.LoginUseCase
import com.example.empty_project.domain.usecase.RegistrationUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun bindRegistrationUseCase(repository: UserRepository): RegistrationUseCase {
        return RegistrationUseCase(repository)
    }

    @Provides
    fun bindLoginUseCase(repository: UserRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    fun bindCreateLoanUseCase(repository: LoanRepository): CreateLoanUseCase {
        return CreateLoanUseCase(repository)
    }
}