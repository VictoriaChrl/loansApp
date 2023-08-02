package com.example.empty_project.feature.loan.creation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.empty_project.domain.entity.LoanConditions
import com.example.empty_project.domain.entity.NewLoan
import com.example.empty_project.feature.loan.creation.R
import com.example.empty_project.feature.loan.creation.databinding.FragmentLoanCreationBinding
import com.example.empty_project.feature.loan.creation.presentation.LoanCreationUiState
import com.example.empty_project.feature.loan.creation.presentation.LoanCreationViewModel
import com.example.empty_project.shared.loan.core.R.string.*
import com.example.empty_project.ui.util.areEditTextsBlank
import com.example.empty_project.ui.util.formatLoanPeriod
import com.example.empty_project.ui.util.isIncorrectAmount
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

        binding.buttonUpdateLoanConditions.setOnClickListener {
            viewModel.getLoanConditions()
        }

        binding.addButton.setOnClickListener {
            createLoan()
        }

        viewModel.apply {
           state.observe(viewLifecycleOwner, ::processState)
        }
    }


    private fun createLoan() {
        binding.apply {
            loanCondition?.let {
                if (areEditTextsBlank(
                        editTextAmount,
                        editTextFirstName,
                        editTextLastName,
                        editTextPhoneNumber
                    )
                ) {
                    showSnackbar(getString(edit_text_empty))
                } else if (editTextAmount.isIncorrectAmount(0, it.maxAmount)) {
                    showSnackbar(getString(edit_text_amount_incorrect))
                } else {
                    val newLoan = NewLoan(
                        amount = editTextAmount.text.toString().toLong(),
                        firstName = editTextFirstName.text.toString(),
                        lastName = editTextLastName.text.toString(),
                        percent = it.percent,
                        period = it.period,
                        phoneNumber = editTextPhoneNumber.text.toString()
                    )

                    viewModel.createLoan(newLoan)
                }
            } ?: enableAddButton()
        }

    }

    private fun enableAddButton(){
        binding.addButton.isEnabled = false
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
            buttonUpdateLoanConditions.isEnabled = true
            addButton.isEnabled = true
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

            buttonUpdateLoanConditions.isVisible = false
            addButton.isEnabled = true

            loanCondition = LoanConditions(
                state.loanConditions.maxAmount,
                state.loanConditions.percent,
                state.loanConditions.period
            )

            maxAmount.text = getString(
                loan_max_amount_condition,
                state.loanConditions.maxAmount.toString()
            )
            period.text = formatLoanPeriod(requireContext(), state.loanConditions.period)
            percent.text =
                getString(loan_percent_condition, state.loanConditions.percent.toString())
        }
    }

    private fun renderCompleteLoanCreationState() {
        binding.progressBar.isVisible = false
        showSnackbar(getString(loan_creation_success))
        findNavController().navigateUp()
    }

    private fun renderErrorNoInternetState() {
        binding.apply {
            addButton.isEnabled = loanCondition != null
            progressBar.isVisible = false
            editTextFirstName.isEnabled = true
            editTextLastName.isEnabled = true
            editTextAmount.isEnabled = true
            editTextPhoneNumber.isEnabled = true
            showSnackbar(getString(error_internet))
        }
    }

    private fun renderErrorUnknownState() {
        binding.apply {
            shimmerAmount.animation = null
            shimmerPeriod.animation = null
            shimmerPercent.animation = null
            progressBar.isVisible = false
            addButton.isEnabled = loanCondition==null
            editTextFirstName.isEnabled = true
            editTextLastName.isEnabled = true
            editTextAmount.isEnabled = true
            editTextPhoneNumber.isEnabled = true
            showSnackbar(getString(unknown_error))
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