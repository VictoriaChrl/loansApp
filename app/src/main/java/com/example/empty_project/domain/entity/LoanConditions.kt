package com.example.empty_project.domain.entity

data class LoanConditions(
    val maxAmount: Long,
    val percent: Double,
    val period: Int
)