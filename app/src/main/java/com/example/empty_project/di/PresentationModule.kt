package com.example.empty_project.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.empty_project.presentation.AuthorizationViewModel
import com.example.empty_project.presentation.LoanCreationViewModel
import com.example.empty_project.presentation.LoanHistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    fun bindAuthorizationViewModel(viewModel: AuthorizationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoanHistoryViewModel::class)
    fun bindLoanHistoryViewModel(viewModel: LoanHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoanCreationViewModel::class)
    fun bindLoanCreationViewModel(viewModel: LoanCreationViewModel): ViewModel

}