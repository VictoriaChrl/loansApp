package com.example.empty_project.shared.loan.core.domain.usecase

import com.example.empty_project.shared.loan.core.domain.entity.NewLoan
import com.example.empty_project.shared.loan.core.domain.repository.LoanRepository

class CreateLoanUseCase(
    private val repository: LoanRepository
) {

    suspend operator fun invoke(newLoan: NewLoan) {
        repository.createLoan(newLoan)
    }
}