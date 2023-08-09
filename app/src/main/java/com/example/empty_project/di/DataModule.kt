package com.example.empty_project.di

import android.app.Application
import android.content.Context
import com.example.empty_project.shared.loan.core.data.SharPrefManager
import com.example.empty_project.shared.loan.core.data.SharPrefManagerImpl
import com.example.empty_project.shared.loan.core.data.repository.LoanRepositoryImpl
import com.example.empty_project.shared.loan.core.data.repository.UserRepositoryImpl
import com.example.empty_project.shared.loan.core.domain.repository.LoanRepository
import com.example.empty_project.shared.loan.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    fun provideLoanRepository(loanRepositoryImpl: LoanRepositoryImpl): LoanRepository

    @Binds
    fun bindSharedPreferences(manager: SharPrefManagerImpl): SharPrefManager
}

@Module
class ContextModule {
    @Provides
    fun provideAppContext(app: Application): Context {
        return app.applicationContext
    }
}
