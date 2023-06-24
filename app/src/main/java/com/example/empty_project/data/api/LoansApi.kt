package com.example.empty_project.data.api

import com.example.empty_project.data.model.AuthModel
import com.example.empty_project.data.model.LoanConditionsModel
import com.example.empty_project.data.model.LoanModel
import com.example.empty_project.data.model.NewLoanModel
import com.example.empty_project.domain.entity.NewLoan
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

private const val AUTH_TOKEN =
    "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2aWN0b3JpYSIsImV4cCI6MTY4OTQwNjk4N30.ZnCGbfu50mddHi40jdlVlb8U1hHPQxbkuJtJ0AOChBmrZFSHRSU-rzfTM0oB3cxD7UW2553spbsJ0d2MWyEuRw"

interface LoansApi {

    @Headers("Accept: */*", "Content-Type: application/json")
    @POST("registration")
    suspend fun registerUser(@Body auth: AuthModel): ResponseBody

    @Headers("Accept: */*", "Content-Type: application/json")
    @POST("login")
    suspend fun loginUser(@Body auth: AuthModel): ResponseBody

    @Headers("Accept: */*", "Content-Type: application/json")
    @POST("loans")
    suspend fun createLoan(@Body newLoan: NewLoanModel, @Header("Authorization") token: String?): ResponseBody

    @Headers("Accept: */*")
    @GET("loans/conditions")
    suspend fun getLoanConditions(@Header("Authorization") token: String?): LoanConditionsModel

    @Headers("Accept: */*")
    @GET("loans/all")
    suspend fun getAllLoans(@Header("Authorization") token: String?): Array<LoanModel>

    @Headers("Accept: */*")
    @GET("loans/{id}")
    suspend fun getLoanById(@Header("Authorization") token: String?, @Path("id") loanId: Long): LoanModel
}
