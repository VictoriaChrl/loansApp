package com.example.empty_project.feature.loan.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.empty_project.feature.loan.onboarding.ui.InstructionItemFragment


class InstructionAdapter(
    private val items: List<InstructionItem>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        val item: InstructionItem = items[position]
        return InstructionItemFragment.newInstance(
            textRes = item.textRes,
            drawableRes = item.drawableRes
        )
    }
}
