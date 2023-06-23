package com.example.empty_project.data.converter

import com.example.empty_project.data.model.LoanConditionsModel
import com.example.empty_project.data.model.LoanModel
import com.example.empty_project.data.model.NewLoanModel
import com.example.empty_project.domain.entity.Loan
import com.example.empty_project.domain.entity.LoanConditions
import com.example.empty_project.domain.entity.NewLoan
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class LoanConverter @Inject constructor() {

    fun convertLoan(from: LoanModel): Loan =
        Loan(
            amount = from.amount,
            firstName = from.firstName,
            lastName = from.lastName,
            percent = from.percent,
            period = from.period,
            phoneNumber = from.phoneNumber,
            id = from.id,
            date = toDate(from.date),
            status = from.status,
        )


    private fun toDate(stringDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val date = inputFormat.parse(stringDate)
        val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return outputFormat.format(date as Date)
    }

    fun convertNewLoan(from: NewLoan): NewLoanModel =
        NewLoanModel(
            amount = from.amount,
            firstName = from.firstName,
            lastName = from.lastName,
            percent = from.percent,
            period = from.period,
            phoneNumber = from.phoneNumber
        )

    fun convertLoanConditions(from: LoanConditionsModel): LoanConditions =
        LoanConditions(
            maxAmount = from.maxAmount,
            percent = from.percent,
            period = from.period
        )
}