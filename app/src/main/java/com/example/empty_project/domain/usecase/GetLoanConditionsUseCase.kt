package com.example.empty_project.domain.usecase

import com.example.empty_project.domain.entity.LoanConditions
import com.example.empty_project.domain.repository.LoanRepository

class GetLoanConditionsUseCase(
    private val repository: LoanRepository
) {

    suspend operator fun invoke(): LoanConditions = repository.getLoanConditions()
}