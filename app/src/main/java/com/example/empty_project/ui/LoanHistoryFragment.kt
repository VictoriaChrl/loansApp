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
import com.example.empty_project.databinding.FragmentLoanHistoryBinding
import com.example.empty_project.presentation.AuthorizationUiState
import com.example.empty_project.presentation.LoanHistoryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoanHistoryFragment : Fragment() {

    private var _binding: FragmentLoanHistoryBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LoanHistoryViewModel

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
        _binding = FragmentLoanHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[LoanHistoryViewModel::class.java]



        binding.addButton.setOnClickListener {
            findNavController().navigate(LoanHistoryFragmentDirections.actionLoanHistoryFragmentToLoanCreationFragment())
        }


        viewModel.login()

        viewModel.state.observe(viewLifecycleOwner) {

        }
    }

    private fun processState(state: AuthorizationUiState) {
        when (state) {
            is AuthorizationUiState.Initial -> Unit
            is AuthorizationUiState.Loading -> renderLoadingState()
            is AuthorizationUiState.Complete -> renderCompleteState(state)
            is AuthorizationUiState.Error -> renderErrorState(state)
        }
    }

    private fun renderLoadingState() {

    }

    private fun renderCompleteState(state: AuthorizationUiState.Complete) {

    }

    private fun renderErrorState(state: AuthorizationUiState.Error) {

    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root.rootView, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}