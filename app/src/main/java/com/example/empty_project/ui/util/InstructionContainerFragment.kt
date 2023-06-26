package com.example.empty_project.ui.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.empty_project.R
import com.example.empty_project.databinding.FragmentCreateLoanInstructionBinding
import com.example.empty_project.ui.adapter.InstructionItem
import com.example.empty_project.ui.adapter.LoanCreationAdapter

class InstructionContainerFragment: Fragment() {

    private var _binding: FragmentCreateLoanInstructionBinding? = null
    private val binding get() = _binding!!

    private val items: List<InstructionItem> = listOf(
        InstructionItem(
            textRes = R.string.instruction_add_loan,
            drawableRes = R.drawable.add_loan_instr
        ),
        InstructionItem(
            textRes = R.string.instruction_fill_gaps,
            drawableRes = R.drawable.add_loan_instr
        )
    )



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentCreateLoanInstructionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
           viewPager.adapter = LoanCreationAdapter(items, requireActivity())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}