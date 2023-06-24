package com.example.empty_project.ui

import android.content.Context
import com.example.empty_project.R

fun formatLoanPeriod(context: Context, day: Int): String {
    return when {
        day % 10 == 1 && day % 100 != 11 -> context.getString(R.string.loan_period_day, day.toString())
        day % 10 in 2..4 && day % 100 !in 12..14 -> context.getString(R.string.loan_period_of_day, day.toString())
        else -> context.getString(R.string.loan_period_days, day.toString())
    }
}