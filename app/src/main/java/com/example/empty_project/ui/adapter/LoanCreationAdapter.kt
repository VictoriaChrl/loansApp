package com.example.empty_project.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.empty_project.ui.LoanCreationInstructionFragment


class LoanCreationAdapter(
    private val items: List<InstructionItem>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        val item: InstructionItem = items[position]
        return LoanCreationInstructionFragment.newInstance(
            textRes = item.textRes,
            drawableRes = item.drawableRes
        )
    }
}
