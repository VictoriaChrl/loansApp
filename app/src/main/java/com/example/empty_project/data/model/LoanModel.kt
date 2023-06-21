package com.example.empty_project.data.model

import com.example.empty_project.domain.entity.LoanStatus
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class LoanModel(
	val amount: Long,
	val firstName: String,
	val lastName: String,
	val percent: Double,
	val period: Int,
	val phoneNumber: String,
	val id: Long,
	val date: LocalDateTime,

	@field:SerializedName("state")
	val status: LoanStatus
)
