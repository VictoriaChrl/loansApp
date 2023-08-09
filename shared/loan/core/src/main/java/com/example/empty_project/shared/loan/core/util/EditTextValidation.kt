package com.example.empty_project.shared.loan.core.util

import android.widget.EditText

fun areEditTextsBlank(vararg editTexts: EditText): Boolean {
    return editTexts.any { it.text.isBlank() }
}

fun EditText.isIncorrectAmount(minAmount: Int, maxAmount: Long): Boolean {
    val amount = text?.toString()?.toLongOrNull()
    return amount == null || amount < minAmount || amount > maxAmount
}