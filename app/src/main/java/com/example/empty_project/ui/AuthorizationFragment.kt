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
import com.example.empty_project.databinding.FragmentAuthorizationBinding
import com.example.empty_project.presentation.AuthorizationUiState
import com.example.empty_project.presentation.AuthorizationViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: AuthorizationViewModel

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
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[AuthorizationViewModel::class.java]

        binding.apply {
            clickableTextAlreadyHaveAccount.setOnClickListener {
                findNavController().navigate(
                    AuthorizationFragmentDirections.actionAuthorizationFragmentToLoginFragment()
                )
            }
            button.setOnClickListener {
                if (editTextName.text?.isBlank() == true || editTextPassword.text?.isBlank() == true) {
                    showSnackbar(getString(R.string.edit_text_empty))
                } else {
                    viewModel.registerUser(
                        editTextName.text.toString(),
                        editTextPassword.text.toString()
                    )
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, ::processState)
    }

    private fun processState(state: AuthorizationUiState) {
        when (state) {
            is AuthorizationUiState.Initial -> Unit
            is AuthorizationUiState.Loading -> renderLoadingState()
            is AuthorizationUiState.Complete -> renderCompleteState(state)
            is AuthorizationUiState.Error.NoInternet -> renderNoInternetState()
            is AuthorizationUiState.Error.AlreadyExist -> renderAlreadyExistState()
        }
    }

    private fun renderLoadingState() {
        binding.progressBar.isVisible = true
        binding.button.isEnabled = false
        binding.editTextName.isEnabled = false
        binding.editTextPassword.isEnabled = false
    }

    private fun renderCompleteState(state: AuthorizationUiState.Complete) {
        binding.progressBar.isVisible = false
        showSnackbar(state.message)
        findNavController().navigate(
            AuthorizationFragmentDirections.actionAuthorizationFragmentToLoginFragment()
        )
    }

    private fun renderNoInternetState() {
        binding.progressBar.isVisible = false
        binding.button.isEnabled = true
        binding.editTextName.isEnabled = true
        binding.editTextPassword.isEnabled = true
        showSnackbar(getString(R.string.error_internet))
    }

    private fun renderAlreadyExistState() {
        binding.progressBar.isVisible = false
        binding.button.isEnabled = true
        binding.editTextName.isEnabled = true
        binding.editTextPassword.isEnabled = true
        showSnackbar(getString(R.string.error_user_already_exists))
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root.rootView, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}