package com.example.empty_project.domain.repository

import com.example.empty_project.domain.entity.Loan
import com.example.empty_project.domain.entity.NewLoan

interface LoanRepository {

    suspend fun getAll(): List<Loan>

    suspend fun getById(id: Long): Loan

    suspend fun createLoan(newLoan: NewLoan)
}