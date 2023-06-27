package com.example.empty_project.data.repository

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

    override suspend fun getAllLoans(): List<Loan> {
        val token = sharPrefManager.getToken()
        val loans = loansApi.getAllLoans(token)

        return loans
            .map { loan -> converterLoan.convertLoan(loan) }
    }

    override suspend fun getLoanById(id: Long): Loan {
        val token = sharPrefManager.getToken()
        return converterLoan.convertLoan(loansApi.getLoanById(token, id))
    }
    override suspend fun createLoan(newLoan: NewLoan) {
        val token = sharPrefManager.getToken()
        loansApi.createLoan(converterLoan.convertNewLoan(newLoan), token)
    }

    override suspend fun getLoanConditions(): LoanConditions {
        val token = sharPrefManager.getToken()
        return converterLoan.convertLoanConditions(loansApi.getLoanConditions(token))

    }
}