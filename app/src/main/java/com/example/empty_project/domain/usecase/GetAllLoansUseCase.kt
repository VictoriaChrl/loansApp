package com.example.empty_project.domain.usecase

import com.example.empty_project.domain.entity.Loan
import com.example.empty_project.domain.entity.LoanStatus
import com.example.empty_project.domain.repository.LoanRepository
import com.example.empty_project.domain.repository.UserRepository
import java.time.LocalDateTime

class GetAllLoansUseCase(private val repository: LoanRepository) {

	suspend operator fun invoke(): List<Loan> = repository.getAllLoans()
}