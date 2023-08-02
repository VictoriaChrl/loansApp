package com.example.empty_project.feature.loan.history.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.empty_project.domain.entity.Loan
import com.example.empty_project.domain.entity.util.formatLoanStatus
import com.example.empty_project.feature.loan.history.databinding.ItemLoanBinding
import com.example.empty_project.shared.loan.core.R.*

class LoanHolder(
    private val binding: ItemLoanBinding,
    private val onItemClicked: (id: Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(loan: Loan) {
        binding.root.setOnClickListener { onItemClicked(loan.id) }

        binding.apply {
            amountText.text =
                itemView.context.getString(string.loan_amount_holder, loan.amount.toString())
            dateText.text = loan.date
            statusText.text = formatLoanStatus(itemView.context, loan.status)
        }
    }
}