package com.example.empty_project.shared.loan.core.domain.entity

data class LoanConditions(
    val maxAmount: Long,
    val percent: Double,
    val period: Int
)