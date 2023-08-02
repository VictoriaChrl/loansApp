package com.example.empty_project.domain.usecase

import com.example.empty_project.domain.entity.NewLoan
import com.example.empty_project.domain.repository.LoanRepository

class CreateLoanUseCase(
    private val repository: LoanRepository
) {

    suspend operator fun invoke(newLoan: NewLoan) {
        repository.createLoan(newLoan)
    }
}