package com.example.empty_project.shared.loan.core.domain.repository

import com.example.empty_project.shared.loan.core.domain.entity.Loan
import com.example.empty_project.shared.loan.core.domain.entity.LoanConditions
import com.example.empty_project.shared.loan.core.domain.entity.NewLoan

interface LoanRepository {

    suspend fun getAllLoans(): List<Loan>

    suspend fun getLoanById(id: Long): Loan

    suspend fun createLoan(newLoan: NewLoan)

    suspend fun getLoanConditions(): LoanConditions


}