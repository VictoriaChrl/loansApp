package com.example.empty_project.feature.loan.history.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.empty_project.feature.loan.history.R
import com.example.empty_project.shared.loan.core.domain.entity.Loan
import com.example.empty_project.feature.loan.history.databinding.FragmentLoanHistoryBinding
import com.example.empty_project.feature.loan.history.presentation.LoanHistoryUiState
import com.example.empty_project.feature.loan.history.presentation.LoanHistoryViewModel
import com.example.empty_project.shared.loan.core.R.*
import com.example.empty_project.shared.loan.core.util.navigate
import dagger.android.support.AndroidSupportInjection
import java.io.Serializable
import javax.inject.Inject

class LoanHistoryFragment : Fragment(){

    private var _binding: FragmentLoanHistoryBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LoanHistoryViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        val callback: OnBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this.remove()
                activity?.finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentLoanHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[LoanHistoryViewModel::class.java]
        viewModel.getLoans()

        binding.apply {

            swipeRefreshLayout.setOnRefreshListener{
                refreshHistoryWithSwipe()
            }

            addButton.setOnClickListener {
                toLoanCreationFragment()
            }

            buttonUpdate.setOnClickListener {
                viewModel.getLoans()
            }

            toolbar.menu.findItem(com.example.empty_project.shared.loan.core.R.id.action_add).setOnMenuItemClickListener {
                toLoanInstructionCreationFragment()
                true
            }
        }

        viewModel.state.observe(viewLifecycleOwner, ::processState)
    }

    private fun refreshHistoryWithSwipe(){
        viewModel.getLoans()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun toLoanCreationFragment() {
       navigate(R.id.action_loanHistoryFragment_to_loanCreationFragment)
    }

    private fun toLoanInstructionCreationFragment() {
        navigate(R.id.action_loanHistoryFragment_to_instructionContainerFragment)
    }


    private fun processState(state: LoanHistoryUiState) {
        when (state) {
            is LoanHistoryUiState.Complete -> renderCompleteState(state)
            LoanHistoryUiState.Error.NoInternet -> renderErrorNoInternetState()
            is LoanHistoryUiState.Error.Unknown -> renderErrorUnknownState()
            LoanHistoryUiState.Initial -> Unit
            LoanHistoryUiState.Loading -> renderLoadingState()
        }
    }

    private fun renderLoadingState() {
        binding.apply {
            progressBar.isVisible = true
            errorText.isVisible = false
            buttonUpdate.isVisible = false
            historyList.isVisible = false
        }
    }

    private fun renderCompleteState(state: LoanHistoryUiState.Complete) {
        binding.progressBar.isVisible = false
        binding.historyList.isVisible = true
        if (state.list.isEmpty()) {
            binding.errorText.text = getString(string.loans_empty)
            binding.errorText.isVisible = true
        }
        loadLoanList(state.list)
    }

    private fun loadLoanList(list: List<Loan>) {
        val loanAdapter = LoanHistoryAdapter { id -> toLoanDetailsFragment(id) }
        binding.historyList.adapter = loanAdapter
        loanAdapter.submitList(list)
    }

    private fun toLoanDetailsFragment(id: Long) {
        navigate(R.id.action_loanHistoryFragment_to_loanDetailsFragment, data = id)
    }

    private fun renderErrorNoInternetState() {
        binding.apply {
            progressBar.isVisible = false
            errorText.isVisible = true
            errorText.text = getString(string.error_internet)
            buttonUpdate.isVisible = true
            historyList.isVisible = false
        }
    }

    private fun renderErrorUnknownState() {
        binding.apply {
            progressBar.isVisible = false
            errorText.isVisible = true
            errorText.text = getString(string.unknown_error)
            buttonUpdate.isVisible = true
            historyList.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}