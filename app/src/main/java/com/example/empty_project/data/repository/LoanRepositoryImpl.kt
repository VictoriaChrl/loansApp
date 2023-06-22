package com.example.empty_project.data.repository

import android.util.Log
import com.example.empty_project.data.SharPrefManagerImpl
import com.example.empty_project.data.api.LoansApi
import com.example.empty_project.data.converter.LoanConverter
import com.example.empty_project.domain.entity.Loan
import com.example.empty_project.domain.entity.LoanConditions
import com.example.empty_project.domain.entity.NewLoan
import com.example.empty_project.domain.repository.LoanRepository
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val loansApi: LoansApi,
    private val converterLoan: LoanConverter,
    private val sharPrefManager: SharPrefManagerImpl
) : LoanRepository {

    override suspend fun getAll(): List<Loan> =
        loansApi.getAll()
            .map { loan -> converterLoan.convertLoan(loan) }

    override suspend fun getById(id: Long): Loan =
        converterLoan.convertLoan(loansApi.getLoanById(id))

    override suspend fun createLoan(newLoan: NewLoan) {
        val token = sharPrefManager.getToken()
        val response = loansApi.createLoan(converterLoan.convertNewLoan(newLoan), token)
        Log.v("createLoan",response.string())
    }

    override suspend fun getLoanConditions(): LoanConditions {
        val token = sharPrefManager.getToken()
        return converterLoan.convertLoanConditions(loansApi.getLoanConditions(token))

    }
}