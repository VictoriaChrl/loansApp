package com.example.empty_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.empty_project.R
import com.example.empty_project.databinding.FragmentInstructionBinding
import com.example.empty_project.ui.adapter.InstructionItem
import com.example.empty_project.ui.adapter.InstructionAdapter
import com.google.android.material.tabs.TabLayoutMediator

class InstructionFragment : Fragment() {

    private var _binding: FragmentInstructionBinding? = null
    private val binding get() = _binding!!

    private val items: List<InstructionItem> = listOf(
        InstructionItem(
            textRes = R.string.instruction_add_loan,
            drawableRes = R.drawable.add_loan_instr
        ),
        InstructionItem(
            textRes = R.string.instruction_fill_gaps,
            drawableRes = R.drawable.fill_gaps_instr
        ),
        InstructionItem(
            textRes = R.string.instruction_approved,
            drawableRes = R.drawable.approved_instr
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentInstructionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewPager.adapter = InstructionAdapter(items, requireActivity())
            TabLayoutMediator(tabLayout,viewPager){_,_->}.attach()
            buttonOk.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}