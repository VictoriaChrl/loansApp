package com.example.empty_project.domain.entity.util

import android.content.Context
import com.example.empty_project.R
import com.example.empty_project.domain.entity.LoanStatus

fun formatLoanStatus(context: Context, status: LoanStatus) =
    when (status) {
        LoanStatus.APPROVED -> context.getString(R.string.loan_status_approved)
        LoanStatus.REGISTERED -> context.getString(R.string.loan_status_registered)
        LoanStatus.REJECTED -> context.getString(R.string.loan_status_rejected)
    }