package com.example.empty_project.domain.usecase

import com.example.empty_project.domain.entity.Loan
import com.example.empty_project.domain.repository.LoanRepository

class GetLoanByIdUseCase(private val repository: LoanRepository) {

    suspend operator fun invoke(id: Long): Loan = repository.getLoanById(id)
}