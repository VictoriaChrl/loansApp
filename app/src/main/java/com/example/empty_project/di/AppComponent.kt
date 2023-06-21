package com.example.empty_project.di

import android.app.Application
import com.example.empty_project.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ActivityModule::class,
    AndroidSupportInjectionModule::class,
    FragmentModule::class,
    NetworkModule::class,
    DataModule::class,
    DomainModule::class,
    ContextModule::class,
    PresentationModule::class])
interface AppComponent {

    fun inject(application: App)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun applicationBind(application: Application): Builder

    }
}