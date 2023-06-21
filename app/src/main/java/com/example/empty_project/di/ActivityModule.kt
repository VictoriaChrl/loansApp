package com.example.empty_project.di

import com.example.empty_project.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun injectActivity(): MainActivity
}