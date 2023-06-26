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
import com.example.empty_project.databinding.FragmentRegistrationBinding
import com.example.empty_project.presentation.states.RegistrationUiState
import com.example.empty_project.presentation.viewmodels.RegistrationViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: RegistrationViewModel

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
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[RegistrationViewModel::class.java]

        binding.apply {
            clickableTextAlreadyHaveAccount.setOnClickListener {
                findNavController().navigate(
                    RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
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

    private fun processState(state: RegistrationUiState) {
        when (state) {
            is RegistrationUiState.Initial -> Unit
            is RegistrationUiState.Loading -> renderLoadingState()
            is RegistrationUiState.Complete -> renderCompleteState()
            is RegistrationUiState.Error.NoInternet -> renderNoInternetState()
            is RegistrationUiState.Error.AlreadyExist -> renderAlreadyExistState()
            RegistrationUiState.End -> Unit
        }
    }

    private fun renderLoadingState() {
        binding.progressBar.isVisible = true
        binding.button.isEnabled = false
        binding.editTextName.isEnabled = false
        binding.editTextPassword.isEnabled = false
    }

    private fun renderCompleteState() {
        binding.progressBar.isVisible = false
        showSnackbar(getString(R.string.unknown_error))
        findNavController().navigate(
            RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
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