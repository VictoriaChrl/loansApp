package com.example.empty_project.shared.loan.core.domain.usecase

import com.example.empty_project.shared.loan.core.domain.entity.Loan
import com.example.empty_project.shared.loan.core.domain.repository.LoanRepository

class GetAllLoansUseCase(private val repository: LoanRepository) {

    suspend operator fun invoke(): List<Loan> = repository.getAllLoans()
}