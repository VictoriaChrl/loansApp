package com.example.empty_project.shared.loan.core.domain.usecase

import com.example.empty_project.shared.loan.core.domain.entity.Loan
import com.example.empty_project.shared.loan.core.domain.repository.LoanRepository

class GetLoanByIdUseCase(private val repository: LoanRepository) {

    suspend operator fun invoke(id: Long): Loan = repository.getLoanById(id)
}