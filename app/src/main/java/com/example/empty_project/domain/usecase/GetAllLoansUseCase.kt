package com.example.empty_project.domain.usecase

import com.example.empty_project.domain.entity.Loan
import com.example.empty_project.domain.entity.LoanStatus
import com.example.empty_project.domain.repository.LoanRepository
import com.example.empty_project.domain.repository.UserRepository
import java.time.LocalDateTime

class GetAllLoansUseCase(private val repository: LoanRepository) {

	suspend operator fun invoke(mockEnabled: Boolean): List<Loan> {
		return if(mockEnabled) {
			getMockedLoansList()
		} else {
			repository.getAll()
		}
	}

	private fun getMockedLoansList(): List<Loan> {
		return listOf(
			Loan(
				amount = 322,
				firstName = "Ментимер",
				lastName = "Шаймиев",
				percent = 365.0,
				period = 365,
				phoneNumber = "89132283228",
				id = 13373228,
				date = LocalDateTime.now(),
				status = LoanStatus.APPROVED,
			),
			Loan(
				amount = 322,
				firstName = "Рудольф",
				lastName = "Нуриев",
				percent = 365.0,
				period = 365,
				phoneNumber = "89132283228",
				id = 13373228,
				date = LocalDateTime.now(),
				status = LoanStatus.REGISTERED,
			),
			Loan(
				amount = 322,
				firstName = "Гуйбадулина",
				lastName = "Софья",
				percent = 365.0,
				period = 365,
				phoneNumber = "89132283228",
				id = 13373228,
				date = LocalDateTime.now(),
				status = LoanStatus.REJECTED,
			),
		)
	}
}