package com.example.empty_project.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.empty_project.databinding.ItemLoanBinding
import com.example.empty_project.domain.entity.Loan

class LoanHistoryAdapter(private val onItemClicked: (id: Long) -> Unit) :
    ListAdapter<Loan, LoanHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanHolder {
        return LoanHolder(
            ItemLoanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClicked
        )
    }

    override fun onBindViewHolder(holder: LoanHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        private val DiffCallback = object : DiffUtil.ItemCallback<Loan>() {
            override fun areItemsTheSame(oldItem: Loan, newItem: Loan): Boolean {
                return (oldItem.id == newItem.id)
            }

            override fun areContentsTheSame(oldItem: Loan, newItem: Loan): Boolean {
                return (oldItem.amount == newItem.amount &&
                        oldItem.date == newItem.date &&
                        oldItem.firstName == newItem.firstName &&
                        oldItem.lastName == newItem.lastName &&
                        oldItem.percent == newItem.percent &&
                        oldItem.period == newItem.period &&
                        oldItem.phoneNumber == newItem.phoneNumber &&
                        oldItem.status == newItem.status
                        )
            }
        }
    }
}



