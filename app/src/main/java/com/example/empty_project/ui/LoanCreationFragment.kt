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
import com.example.empty_project.domain.entity.LoanConditions
import com.example.empty_project.domain.entity.NewLoan
import com.example.empty_project.domain.entity.util.formatLoanStatus
import com.example.empty_project.presentation.states.LoanCreationUiState
import com.example.empty_project.presentation.viewmodels.LoanCreationViewModel
import com.example.empty_project.ui.util.formatLoanPeriod
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoanCreationFragment : Fragment() {

    private var _binding: FragmentLoanCreationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: LoanCreationViewModel

    private var loanCondition: LoanConditions? = null

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
                if (loanCondition == null) {
                    viewModel.getLoanConditions()
                } else if (editTextAmount.text?.isBlank() == true ||
                    editTextFirstName.text?.isBlank() == true ||
                    editTextLastName.text?.isBlank() == true ||
                    editTextPhoneNumber.text?.isBlank() == true
                ) {
                    showSnackbar(getString(R.string.edit_text_empty))
                } else if (editTextAmount.text.toString().toLong() > loanCondition?.maxAmount!!
                    ||
                    editTextAmount.text.toString().toInt() < 0
                ) {
                    showSnackbar(getString(R.string.edit_text_amount_incorrect))
                } else {
                    val newLoan = NewLoan(
                        amount = editTextAmount.text.toString().toLong(),
                        firstName = editTextFirstName.text.toString(),
                        lastName = editTextLastName.text.toString(),
                        percent = loanCondition!!.percent,
                        period = loanCondition!!.period,
                        phoneNumber = editTextPhoneNumber.text.toString()
                    )

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
            is LoanCreationUiState.CompleteLoanCreation -> renderCompleteLoanCreationState()
            is LoanCreationUiState.Error.NoInternet -> renderErrorNoInternetState()
            is LoanCreationUiState.Error.Unknown -> renderErrorUnknownState()
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

            loanCondition = LoanConditions(
                state.loanConditions.maxAmount,
                state.loanConditions.percent,
                state.loanConditions.period
            )

            maxAmount.text = getString(
                R.string.loan_max_amount_condition,
                state.loanConditions.maxAmount.toString()
            )
            period.text = formatLoanPeriod(requireContext(), state.loanConditions.period)
            percent.text =
                getString(R.string.loan_percent_condition, state.loanConditions.percent.toString())
        }
    }

    private fun renderCompleteLoanCreationState() {
        binding.progressBar.isVisible = false
        showSnackbar(getString(R.string.loan_creation_success))
        findNavController().navigateUp()
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

    private fun renderErrorUnknownState() {
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
            showSnackbar(getString(R.string.unknown_error))
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