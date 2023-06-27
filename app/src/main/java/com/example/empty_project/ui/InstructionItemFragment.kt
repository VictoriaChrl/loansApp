package com.example.empty_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.empty_project.databinding.ItemInstructionBinding

class InstructionItemFragment : Fragment() {

    private var _binding: ItemInstructionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = ItemInstructionBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private const val KEY_TEXT = "text"
        private const val KEY_IMAGE = "image"

        fun newInstance(
            @StringRes textRes: Int,
            @DrawableRes drawableRes: Int
        ): InstructionItemFragment {
            val args = Bundle()
            args.putInt(KEY_TEXT, textRes)
            args.putInt(KEY_IMAGE, drawableRes)
            return InstructionItemFragment().apply { arguments = args }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            instructionText.setText(requireArguments().getInt(KEY_TEXT))
            instructionImage.setImageResource(requireArguments().getInt(KEY_IMAGE))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}