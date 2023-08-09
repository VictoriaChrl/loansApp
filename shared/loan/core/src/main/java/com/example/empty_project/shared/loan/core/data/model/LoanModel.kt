package com.example.empty_project.shared.loan.core.data.model

import com.example.empty_project.shared.loan.core.domain.entity.LoanStatus
import com.google.gson.annotations.SerializedName

data class LoanModel(
	val amount: Long,
	val firstName: String,
	val lastName: String,
	val percent: Double,
	val period: Int,
	val phoneNumber: String,
	val id: Long,
	val date: String,

	@field:SerializedName("state")
	val status: LoanStatus
)
