package com.example.empty_project.domain.entity

import java.time.LocalDateTime

data class Loan(
	val amount: Long,
	val firstName: String,
	val lastName: String,
	val percent: Double,
	val period: Int,
	val phoneNumber: String,
	val id: Long,
	val date: String,
	val status: LoanStatus
)
