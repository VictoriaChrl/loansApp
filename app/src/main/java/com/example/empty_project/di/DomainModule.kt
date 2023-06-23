package com.example.empty_project.di

import com.example.empty_project.domain.repository.LoanRepository
import com.example.empty_project.domain.repository.UserRepository
import com.example.empty_project.domain.usecase.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideRegistrationUseCase(repository: UserRepository): RegistrationUseCase {
        return RegistrationUseCase(repository)
    }

    @Provides
    fun provideLoginUseCase(repository: UserRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    fun provideCreateLoanUseCase(repository: LoanRepository): CreateLoanUseCase {
        return CreateLoanUseCase(repository)
    }

    @Provides
    fun provideGetLoanConditionsUseCase(repository: LoanRepository): GetLoanConditionsUseCase {
        return GetLoanConditionsUseCase(repository)
    }

    @Provides
    fun provideGetAllLoansUseCase(repository: LoanRepository): GetAllLoansUseCase {
        return GetAllLoansUseCase(repository)
    }
}