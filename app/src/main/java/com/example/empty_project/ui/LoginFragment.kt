package com.example.empty_project.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.empty_project.R
import com.example.empty_project.databinding.FragmentUserLoginBinding
import com.example.empty_project.presentation.states.LoginUiState
import com.example.empty_project.presentation.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoginFragment : Fragment() {

    private var _binding: FragmentUserLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: LoginViewModel

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
        _binding = FragmentUserLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        binding.apply {
            button.setOnClickListener {
                if (editTextName.text?.isBlank() == true || editTextPassword.text?.isBlank() == true) {
                    showSnackbar(getString(R.string.edit_text_empty))
                } else {
                    viewModel.loginUser(
                        editTextName.text.toString(),
                        editTextPassword.text.toString()
                    )
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, ::processState)
    }

    private fun processState(state: LoginUiState) {
        when (state) {
            is LoginUiState.Initial -> Unit
            is LoginUiState.Loading -> renderLoadingState()
            is LoginUiState.Complete -> renderCompleteState()
            is LoginUiState.Error.NoInternet -> renderNoInternetState()
            is LoginUiState.Error.Unknown -> renderUnknownState()
        }
    }

    private fun renderLoadingState() {
        binding.progressBar.isVisible = true
        binding.button.isEnabled = false
        binding.editTextName.isEnabled = false
        binding.editTextPassword.isEnabled = false
    }

    private fun renderCompleteState() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToLoanHistoryFragment()
        )
    }

    private fun renderNoInternetState() {
        binding.progressBar.isVisible = false
        binding.button.isEnabled = true
        binding.editTextName.isEnabled = true
        binding.editTextPassword.isEnabled = true
        showSnackbar(getString(R.string.error_internet))
    }

    private fun renderUnknownState() {
        binding.progressBar.isVisible = false
        binding.button.isEnabled = true
        binding.editTextName.isEnabled = true
        binding.editTextPassword.isEnabled = true
        showSnackbar(getString(R.string.login_unknown_error))
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root.rootView, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}