package com.example.empty_project.shared.loan.core.data.repository

import android.util.Log
import com.example.empty_project.shared.loan.core.data.SharPrefManagerImpl
import com.example.empty_project.shared.loan.core.data.api.LoansApi
import com.example.empty_project.shared.loan.core.data.converter.LoanConverter
import com.example.empty_project.shared.loan.core.data.database.LoanDao
import com.example.empty_project.shared.loan.core.domain.entity.Loan
import com.example.empty_project.shared.loan.core.domain.entity.LoanConditions
import com.example.empty_project.shared.loan.core.domain.entity.NewLoan
import com.example.empty_project.shared.loan.core.domain.repository.LoanRepository
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val loansDao: LoanDao,
    private val loansApi: LoansApi,
    private val converterLoan: LoanConverter,
    private val sharPrefManager: SharPrefManagerImpl
) : LoanRepository {

    override suspend fun getAllLoans(): List<Loan> {
        val token = sharPrefManager.getToken()

        return try {
            val loans =
                loansApi.getAllLoans(token).map { loans -> converterLoan.convertLoan(loans) }
            saveLoansToDatabase(loans)
            loans
        } catch (e: Exception) {
            getLoansFromDatabase()
        }
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

    private suspend fun saveLoansToDatabase(loans: List<Loan>) {
        loansDao.insertLoans(loans.map { loans ->
            converterLoan.convertLoanToLoanEntity(loans)
        })
    }

    private suspend fun getLoansFromDatabase(): List<Loan> {
        return loansDao.getAllLoans().map { loan ->
            converterLoan.convertLoanEntityToLoan(loan)
        }
    }
}