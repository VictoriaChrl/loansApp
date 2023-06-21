package com.example.empty_project.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.empty_project.databinding.FragmentLoanCreationBinding
import com.example.empty_project.domain.entity.NewLoan
import com.example.empty_project.presentation.AuthorizationUiState
import com.example.empty_project.presentation.LoanCreationUiState
import com.example.empty_project.presentation.LoanCreationViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoanCreationFragment : Fragment() {

    private var _binding: FragmentLoanCreationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: LoanCreationViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentLoanCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[LoanCreationViewModel::class.java]

        binding.apply {
            addButton.setOnClickListener {
                val newLoan = NewLoan(
                    amount = editTextAmount.text.toString().toLong(),
                    firstName = editTextFirstName.text.toString(),
                    lastName = editTextLastName.text.toString(),
                    percent = editTextPercent.text.toString().toDouble(),
                    period = editTextPeriod.text.toString().toInt(),
                    phoneNumber = editTextPhoneNumber.text.toString()
                )
                Log.v("newLoan", newLoan.toString())
                viewModel.createLoan(newLoan)
            }
        }

        viewModel.state.observe(viewLifecycleOwner, ::processState)
    }

    private fun processState(state: LoanCreationUiState) {
        when (state) {
            is LoanCreationUiState.Initial -> Unit
            is LoanCreationUiState.Loading -> renderLoadingState()
            is LoanCreationUiState.Complete -> renderCompleteState()
            is LoanCreationUiState.Error -> renderErrorState()
        }
    }

    private fun renderLoadingState() {

    }

    private fun renderCompleteState() {

    }

    private fun renderErrorState() {

    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root.rootView, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}