package com.example.empty_project.domain.repository

import com.example.empty_project.domain.entity.Loan
import com.example.empty_project.domain.entity.LoanConditions
import com.example.empty_project.domain.entity.NewLoan

interface LoanRepository {

    suspend fun getAllLoans(): List<Loan>

    suspend fun getById(id: Long): Loan

    suspend fun createLoan(newLoan: NewLoan)

    suspend fun getLoanConditions(): LoanConditions
}