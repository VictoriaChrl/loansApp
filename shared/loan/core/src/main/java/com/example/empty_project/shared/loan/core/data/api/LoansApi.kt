package com.example.empty_project.shared.loan.core.data.api

import com.example.empty_project.shared.loan.core.data.model.AuthModel
import com.example.empty_project.shared.loan.core.data.model.LoanConditionsModel
import com.example.empty_project.shared.loan.core.data.model.LoanModel
import com.example.empty_project.shared.loan.core.data.model.NewLoanModel
import okhttp3.ResponseBody
import retrofit2.http.*

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
