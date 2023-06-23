package com.example.empty_project.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.empty_project.R
import com.example.empty_project.databinding.FragmentLoanCreationBinding
import com.example.empty_project.domain.entity.NewLoan
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
//
//        viewModel.getLoanConditions()

        binding.apply {
            addButton.setOnClickListener {
                if (maxAmount.text?.isBlank() == true || percent.text?.isBlank() == true || period.text?.isBlank() == true) {
                    showSnackbar(getString(R.string.try_again))
                    viewModel.getLoanConditions()
                } else if (editTextAmount.text?.isBlank() == true ||
                    editTextFirstName.text?.isBlank() == true ||
                    editTextLastName.text?.isBlank() == true ||
                    editTextPhoneNumber.text?.isBlank() == true
                ) {
                    showSnackbar(getString(R.string.edit_text_empty))
                } else if (editTextAmount.text.toString().toInt() > maxAmount.text.toString()
                        .toInt() ||
                    editTextAmount.text.toString().toInt() < 0
                ) {
                    showSnackbar(getString(R.string.edit_text_amount_incorrect))
                } else {
                    val newLoan = NewLoan(
                        amount = editTextAmount.text.toString().toLong(),
                        firstName = editTextFirstName.text.toString(),
                        lastName = editTextLastName.text.toString(),
                        percent = percent.text.toString().toDouble(),
                        period = period.text.toString().toInt(),
                        phoneNumber = editTextPhoneNumber.text.toString()
                    )
                    Log.v("newLoan", newLoan.toString())
                    viewModel.createLoan(newLoan)
                }
            }
        }

        viewModel.state.observe(viewLifecycleOwner, ::processState)
    }

    private fun processState(state: LoanCreationUiState) {
        when (state) {
            is LoanCreationUiState.Initial -> viewModel.getLoanConditions()
            LoanCreationUiState.LoadingConditions -> renderLoadingConditionsState()
            LoanCreationUiState.LoadingLoanCreation -> renderLoadingLoanCreationState()
            is LoanCreationUiState.CompleteLoadingConditions -> renderCompleteLoadingConditionsState(
                state
            )
            is LoanCreationUiState.CompleteLoanCreation -> renderCompleteLoanCreationState(state)
            is LoanCreationUiState.Error.NoInternet -> renderErrorNoInternetState()
            is LoanCreationUiState.Error.Unknown -> renderErrorUnknownState(state)
        }
    }

    private fun renderLoadingConditionsState() {
        val shimmer = AnimationUtils.loadAnimation(requireContext(), R.anim.shimmer)
        binding.apply {
            shimmerAmount.startAnimation(shimmer)
            shimmerPeriod.startAnimation(shimmer)
            shimmerPercent.startAnimation(shimmer)
        }
    }

    private fun renderLoadingLoanCreationState() {
        binding.progressBar.isVisible = true
        binding.addButton.isEnabled = false
        binding.editTextFirstName.isEnabled = false
        binding.editTextLastName.isEnabled = false
        binding.editTextAmount.isEnabled = false
        binding.editTextPhoneNumber.isEnabled = false
    }

    private fun renderCompleteLoadingConditionsState(state: LoanCreationUiState.CompleteLoadingConditions) {
        binding.apply {
            shimmerAmount.apply {
                animation = null
                isVisible = false
            }
            shimmerPeriod.apply {
                animation = null
                isVisible = false
            }
            shimmerPercent.apply {
                animation = null
                isVisible = false
            }

            maxAmount.text = state.loanConditions.maxAmount.toString()
            period.text = state.loanConditions.period.toString()
            percent.text = state.loanConditions.percent.toString()
        }
    }

    private fun renderCompleteLoanCreationState(state: LoanCreationUiState.CompleteLoanCreation) {
        binding.progressBar.isVisible = false
        showSnackbar(state.message)
        findNavController().navigate(
            LoanCreationFragmentDirections.actionLoanCreationFragmentToLoanHistoryFragment()
        )
    }

    private fun renderErrorNoInternetState() {
        binding.apply {
            progressBar.isVisible = false
            addButton.isEnabled = true
            editTextFirstName.isEnabled = true
            editTextLastName.isEnabled = true
            editTextAmount.isEnabled = true
            editTextPhoneNumber.isEnabled = true
            showSnackbar(getString(R.string.error_internet))
        }
    }

    private fun renderErrorUnknownState(state: LoanCreationUiState.Error.Unknown) {
        binding.apply {
            shimmerAmount.animation = null
            shimmerPeriod.animation = null
            shimmerPercent.animation = null
            progressBar.isVisible = false
            addButton.isEnabled = true
            editTextFirstName.isEnabled = true
            editTextLastName.isEnabled = true
            editTextAmount.isEnabled = true
            editTextPhoneNumber.isEnabled = true
            showSnackbar(state.message)
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root.rootView, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}