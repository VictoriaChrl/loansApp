package com.example.empty_project.di

import com.example.empty_project.feature.loan.creation.ui.LoanCreationFragment
import com.example.empty_project.feature.loan.details.ui.LoanDetailsFragment
import com.example.empty_project.feature.loan.history.ui.LoanHistoryFragment
import com.example.empty_project.feature.loan.login.ui.LoginFragment
import com.example.empty_project.feature.loan.onboarding.ui.InstructionFragment
import com.example.empty_project.feature.loan.onboarding.ui.InstructionItemFragment
import com.example.empty_project.feature.loan.register.ui.RegistrationFragment
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