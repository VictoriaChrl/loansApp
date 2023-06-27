package com.example.empty_project.di

import com.example.empty_project.ui.*
import com.example.empty_project.ui.InstructionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment(): RegistrationFragment

    @ContributesAndroidInjector
    abstract fun contributeLoanHistoryFragment(): LoanHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeLoanCreationFragment(): LoanCreationFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeLoanDetailsFragment(): LoanDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeInstructionItemFragment(): InstructionItemFragment

    @ContributesAndroidInjector
    abstract fun contributeInstructionFragment(): InstructionFragment
}