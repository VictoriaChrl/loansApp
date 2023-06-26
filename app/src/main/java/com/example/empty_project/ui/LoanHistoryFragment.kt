package com.example.empty_project.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.empty_project.R
import com.example.empty_project.databinding.FragmentLoanHistoryBinding
import com.example.empty_project.domain.entity.Loan
import com.example.empty_project.presentation.states.LoanHistoryUiState
import com.example.empty_project.presentation.viewmodels.LoanHistoryViewModel
import com.example.empty_project.ui.adapter.LoanHistoryAdapter
import dagger.android.support.AndroidSupportInjection
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
        binding.swipeRefreshLayout.setOnRefreshListener{
            viewModel.getLoans()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[LoanHistoryViewModel::class.java]

        viewModel.getLoans()

        binding.apply {
            addButton.setOnClickListener {
                findNavController().navigate(LoanHistoryFragmentDirections.actionLoanHistoryFragmentToLoanCreationFragment())
            }
            buttonUpdate.setOnClickListener {
                viewModel.getLoans()
            }
            toolbar.menu.findItem(R.id.action_add).setOnMenuItemClickListener {
                findNavController().navigate(LoanHistoryFragmentDirections.actionLoanHistoryFragmentToInstructionContainerFragment())
                true
            }
        }

        viewModel.state.observe(viewLifecycleOwner, ::processState)
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
        }
    }

    private fun renderCompleteState(state: LoanHistoryUiState.Complete) {
        binding.progressBar.isVisible = false
        if (state.list.isEmpty()) {
            binding.errorText.text = getString(R.string.loans_empty)
            binding.errorText.isVisible = true
        }
        loadLoanList(state.list)
    }

    private fun loadLoanList(list: List<Loan>) {
        val loanAdapter = LoanHistoryAdapter { id -> toLoanDetails(id) }
        binding.historyList.adapter = loanAdapter
        loanAdapter.submitList(list)
    }

    private fun toLoanDetails(id: Long) {
        findNavController().navigate(
            LoanHistoryFragmentDirections.actionLoanHistoryFragmentToLoanDetailsFragment(
                id
            )
        )
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