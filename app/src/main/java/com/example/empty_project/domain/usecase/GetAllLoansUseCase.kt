package com.example.empty_project.domain.usecase

import com.example.empty_project.domain.entity.Loan
import com.example.empty_project.domain.repository.LoanRepository

class GetAllLoansUseCase(private val repository: LoanRepository) {

	suspend operator fun invoke(): List<Loan> = repository.getAllLoans()
}