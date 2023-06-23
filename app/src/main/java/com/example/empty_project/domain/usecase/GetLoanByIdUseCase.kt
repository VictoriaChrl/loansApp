package com.example.empty_project.domain.usecase

import com.example.empty_project.domain.entity.Loan
import com.example.empty_project.domain.entity.LoanStatus
import com.example.empty_project.domain.repository.LoanRepository
import com.example.empty_project.domain.repository.UserRepository
import java.time.LocalDateTime

class GetLoanByIdUseCase(private val repository: LoanRepository) {

	suspend operator fun invoke(id: Long, mockEnabled: Boolean): Loan {
		return if (mockEnabled) {
			getMockedLoan()
		} else {
			repository.getById(id)
		}
	}

	private fun getMockedLoan(): Loan =
		Loan(
			amount = 322,
			firstName = "Ментимер",
			lastName = "Шаймиев",
			percent = 365.0,
			period = 365,
			phoneNumber = "89132283228",
			id = 13373228,
			date = "LocalDateTime.now().toString()",
			status = LoanStatus.APPROVED,
		)
}