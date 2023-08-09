package com.example.empty_project.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.empty_project.feature.loan.creation.presentation.LoanCreationViewModel
import com.example.empty_project.feature.loan.details.presentation.LoanDetailsViewModel
import com.example.empty_project.feature.loan.history.presentation.LoanHistoryViewModel
import com.example.empty_project.feature.loan.login.presentation.LoginViewModel
import com.example.empty_project.feature.loan.register.presentation.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoanHistoryViewModel::class)
    fun bindLoanHistoryViewModel(viewModel: LoanHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoanCreationViewModel::class)
    fun bindLoanCreationViewModel(viewModel: LoanCreationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoanDetailsViewModel::class)
    fun bindLoanDetailsViewModel(viewModel: LoanDetailsViewModel): ViewModel
}