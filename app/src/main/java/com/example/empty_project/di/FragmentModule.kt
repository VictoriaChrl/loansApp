package com.example.empty_project.di

import com.example.empty_project.ui.AuthorizationFragment
import com.example.empty_project.ui.LoanCreationFragment
import com.example.empty_project.ui.LoanHistoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeAuthorizationFragment(): AuthorizationFragment

    @ContributesAndroidInjector
    abstract fun contributeLoanHistoryFragment(): LoanHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeLoanCreationFragment(): LoanCreationFragment
}