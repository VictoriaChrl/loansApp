package com.example.empty_project.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.empty_project.R
import com.example.empty_project.databinding.FragmentLoanDetailsBinding
import com.example.empty_project.domain.entity.util.formatLoanStatus
import com.example.empty_project.presentation.states.LoanDetailsUiState
import com.example.empty_project.presentation.viewmodels.LoanDetailsViewModel
import com.example.empty_project.ui.util.formatLoanPeriod
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoanDetailsFragment : Fragment() {

    private var _binding: FragmentLoanDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: LoanDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LoanDetailsViewModel

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
        _binding = FragmentLoanDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[LoanDetailsViewModel::class.java]

        viewModel.getLoan(args.id)

        viewModel.state.observe(viewLifecycleOwner, ::processState)
    }

    private fun processState(state: LoanDetailsUiState) {
        when (state) {
            is LoanDetailsUiState.Complete -> renderCompleteState(state)
            LoanDetailsUiState.Error.NoInternet -> renderErrorNoInternetState()
            LoanDetailsUiState.Error.Unknown -> renderErrorUnknownState()
            LoanDetailsUiState.Initial -> Unit
            LoanDetailsUiState.Loading -> renderLoadingState()
        }
    }

    private fun renderLoadingState() {
        binding.apply {
            progressBar.isVisible = true
            group.isVisible = false
            errorText.isVisible = false
            buttonUpdate.isVisible = false
        }
    }

    private fun renderCompleteState(state: LoanDetailsUiState.Complete) {
        binding.apply {
            progressBar.isVisible = false
            group.isVisible = true
            loanAmount.text = state.loan.amount.toString()
            loanDate.text = state.loan.date
            loanStatus.text = formatLoanStatus(requireContext(), state.loan.status)
            loanName.text = getString(R.string.loan_title, state.loan.date)
            loanPercent.text = getString(R.string.loan_percent, state.loan.percent.toString())
            loanPeriod.text = formatLoanPeriod(requireContext(), state.loan.period)
            userName.text = state.loan.firstName
            userSurname.text = state.loan.lastName
            userPhoneNumber.text = state.loan.phoneNumber
        }

    }

    private fun renderErrorNoInternetState() {
        binding.apply {
            progressBar.isVisible = false
            errorText.isVisible = true
            errorText.text = getString(R.string.error_internet)
            buttonUpdate.isVisible = true
        }
    }

    private fun renderErrorUnknownState() {
        binding.apply {
            progressBar.isVisible = false
            errorText.isVisible = true
            errorText.text = getString(R.string.unknown_error)
            buttonUpdate.isVisible = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}