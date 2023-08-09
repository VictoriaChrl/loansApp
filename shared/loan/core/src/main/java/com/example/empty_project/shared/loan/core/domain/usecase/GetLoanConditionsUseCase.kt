package com.example.empty_project.shared.loan.core.domain.usecase

import com.example.empty_project.shared.loan.core.domain.entity.LoanConditions
import com.example.empty_project.shared.loan.core.domain.repository.LoanRepository

class GetLoanConditionsUseCase(
    private val repository: LoanRepository
) {

    suspend operator fun invoke(): LoanConditions = repository.getLoanConditions()
}