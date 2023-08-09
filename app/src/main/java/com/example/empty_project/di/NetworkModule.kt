package com.example.empty_project.di

import com.example.empty_project.shared.loan.core.data.api.LoansApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class NetworkModule {

    private companion object {
        const val BASE_URL = "https://shiftlab.cft.ru:7777/"
    }

    @Provides
    @LoanBaseUrl
    fun provideBaseUrl(): String =
        BASE_URL

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().setLenient().create())

    @Provides
    fun provideClient(): OkHttpClient =
        OkHttpClient.Builder()
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
        @LoanBaseUrl baseUrl: String,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideCatFactApi(
        retrofit: Retrofit,
    ): LoansApi =
        retrofit.create(LoansApi::class.java)
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LoanBaseUrl