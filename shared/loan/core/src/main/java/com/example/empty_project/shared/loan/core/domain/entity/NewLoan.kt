package com.example.empty_project.shared.loan.core.domain.entity

data class NewLoan(
    val amount: Long,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String
)