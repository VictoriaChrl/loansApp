package com.example.empty_project.shared.loan.core.data.converter

import com.example.empty_project.shared.loan.core.data.database.LoanEntity
import com.example.empty_project.shared.loan.core.data.model.LoanConditionsModel
import com.example.empty_project.shared.loan.core.data.model.LoanModel
import com.example.empty_project.shared.loan.core.data.model.NewLoanModel
import com.example.empty_project.shared.loan.core.domain.entity.Loan
import com.example.empty_project.shared.loan.core.domain.entity.LoanConditions
import com.example.empty_project.shared.loan.core.domain.entity.NewLoan
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class LoanConverter @Inject constructor() {

    fun convertLoanEntityToLoan(from: LoanEntity): Loan =
        Loan(
            amount = from.amount,
            firstName = from.firstName,
            lastName = from.lastName,
            percent = from.percent,
            period = from.period,
            phoneNumber = from.phoneNumber,
            id = from.id,
            date = from.date,
            status = from.status,
        )

    fun convertLoanToLoanEntity(from: Loan): LoanEntity =
        LoanEntity(
            amount = from.amount,
            firstName = from.firstName,
            lastName = from.lastName,
            percent = from.percent,
            period = from.period,
            phoneNumber = from.phoneNumber,
            id = from.id,
            date = from.date,
            status = from.status,
        )

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


    fun toDate(stringDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val date = inputFormat.parse(stringDate)
        val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("YOUR_TIMEZONE")
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